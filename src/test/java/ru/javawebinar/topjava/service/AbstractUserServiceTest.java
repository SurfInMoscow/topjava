package ru.javawebinar.topjava.service;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static ru.javawebinar.topjava.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {
    @Autowired
    protected UserService userService;

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected Environment environment;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Override
    @Test
    public void save() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        User created = userService.create(newUser);
        newUser.setId(created.getId());
        assertMatch(userService.getAll(), ADMIN, newUser, USER);
    }

    @Test
    public void duplicateMailCreate() throws Exception {
        expectedException.expect(DataAccessException.class);
        userService.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Override
    @Test
    public void delete() throws Exception {
        userService.delete(USER_ID);
        assertMatch(userService.getAll(), ADMIN);
    }

    @Override
    @Test
    public void deleteNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        userService.delete(1);
    }

    @Override
    @Test
    public void get() throws Exception {
        User user = userService.get(USER_ID);
        assertMatch(user, USER);
    }

    @Override
    @Test
    public void getNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        userService.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = userService.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }

    @Override
    @Test
    public void getAll() throws Exception {
        List<User> all = userService.getAll();
        assertMatch(all, ADMIN, USER);
    }

    @Override
    @Test
    public void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        updated.getRoles().add(Role.ROLE_ADMIN);
        userService.update(updated);
        assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    public void createWithException() throws Exception {
        Assume.assumeFalse(isJdbcProfile());
        validateRootCause(() -> userService.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())), ConstraintViolationException.class);
    }

    private boolean isJdbcProfile() {
        return Arrays.asList(environment.getActiveProfiles()).contains("jdbc");
    }
}
