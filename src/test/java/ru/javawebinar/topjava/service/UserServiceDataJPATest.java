package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;

@RunWith(SpringRunner.class)
@ActiveProfiles({"postgres", "datajpa"})
public class UserServiceDataJPATest extends AbstractServiceTest {

    @Override
    @Test
    public void save() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
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
        Assert.assertTrue(user.getMeals().size() != 0);
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
        userService.update(updated);
        assertMatch(userService.get(USER_ID), updated);
    }
}