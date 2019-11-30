package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public abstract class AbstractMealServiceTest extends AbstractServiceTest {
    @Autowired
    protected MealService mealService;

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected Environment environment;

    @Before
    public void setUp() throws Exception {
        Objects.requireNonNull(cacheManager.getCache("meals")).clear();
    }

    @Override
    @Test
    public void save() throws Exception {
        Meal userLunch = new Meal(LocalDateTime.of(2019, 5, 4, 15, 0), "ланч", 500);
        Meal created = mealService.save(USER_ID, userLunch);
        created.setId(created.getId());
        assertMatch(mealService.getAll(USER_ID), userSupper, userLunch, userDinner, userBreakfast);
    }

    @Override
    @Test
    public void delete() throws Exception {
        mealService.delete(USER_ID, USER_MEAL1_ID);
        assertMatch(mealService.getAll(USER_ID), userSupper, userDinner);
    }

    @Override
    @Test
    public void deleteNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        mealService.delete(0, USER_MEAL1_ID - 1);
    }

    @Override
    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(100000, USER_MEAL3_ID);
        Assert.assertEquals(meal, userSupper);
        //assertMatch(new Meal(meal.getUser().getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()), userSupper);
    }

    @Override
    @Test
    public void getNotFound() throws Exception {
        expectedException.expect(ru.javawebinar.topjava.util.exception.NotFoundException.class);
        mealService.get(0, 1);
    }

    @Override
    @Test
    public void update() throws Exception {
        Meal updated = new Meal(adminLunch);
        updated.setCalories(100);
        updated.setDescription("чай");
        mealService.update(ADMIN_ID, updated);
        assertMatch(mealService.get(ADMIN_ID, ADMIN_MEAL1_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        expectedException.expect(Exception.class);
        Meal updated = new Meal(LocalDateTime.of(2019, 5, 4, 15, 0), "чай", 100);
        updated.setId(USER_ID);
        mealService.update(ADMIN_ID, updated);
    }

    @Override
    @Test
    public void getAll() throws Exception {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, userSupper, userDinner, userBreakfast);
    }

    @Test
    public void getBetween() {
        List<Meal> mealsBetween = mealService.getBetweenDateTimes(LocalDateTime.of(2019, 5, 4, 9, 0),
                LocalDateTime.of(2019, 5, 4, 14, 0), 100000);
        Assert.assertEquals(2, mealsBetween.size());
    }

    @Test
    public void createWithException() throws Exception {
        Assume.assumeFalse(isJdbcProfile());
        validateRootCause(() -> mealService.save(USER_ID, new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300)), ConstraintViolationException.class);
        validateRootCause(() -> mealService.save(USER_ID, new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9)), ConstraintViolationException.class);
        validateRootCause(() -> mealService.save(USER_ID, new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001)), ConstraintViolationException.class);
    }

    private boolean isJdbcProfile() {
        return Arrays.asList(environment.getActiveProfiles()).contains("jdbc");
    }
}
