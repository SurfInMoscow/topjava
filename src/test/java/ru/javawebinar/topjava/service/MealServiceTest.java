package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private static final Logger log = LoggerFactory.getLogger("result");

    private static StringBuilder results = new StringBuilder();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }

    @Autowired
    private MealService service;

    @Test
    public void save() throws Exception {
        Meal userLunch = new Meal(LocalDateTime.of(2019, 5, 4, 15, 0), "ланч", 500);
        Meal created = service.save(USER_ID, userLunch);
        created.setId(created.getId());
        assertMatch(service.getAll(USER_ID), userSupper, userLunch, userDinner, userBreakfast);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID, USER_MEAL1_ID);
        assertMatch(service.getAll(USER_ID), userSupper, userDinner);
    }

    @Test//(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        service.delete(0, USER_MEAL1_ID - 1);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(100000, USER_MEAL3_ID);
        Assert.assertEquals(meal, userSupper);
        //assertMatch(new Meal(meal.getUser().getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()), userSupper);
    }

    @Test//(expected = org.springframework.dao.EmptyResultDataAccessException.class)
    public void getNotFound() throws Exception {
        expectedException.expect(ru.javawebinar.topjava.util.exception.NotFoundException.class);
        service.get(0, 1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(adminLunch);
        updated.setCalories(100);
        updated.setDescription("чай");
        service.update(ADMIN_ID, updated);
        assertMatch(service.get(ADMIN_ID, ADMIN_MEAL1_ID), updated);
    }

    @Test//(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void updateNotFound() throws Exception {
        expectedException.expect(org.springframework.dao.DataIntegrityViolationException.class);
        Meal updated = new Meal(LocalDateTime.of(2019, 5, 4, 15, 0), "чай", 100);
        updated.setId(USER_ID);
        service.update(ADMIN_ID, updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, userSupper, userDinner, userBreakfast);
    }

    @Test
    public void getBetween() {
        List<Meal> mealsBetween = service.getBetweenDateTimes(LocalDateTime.of(2019, 5, 4, 9, 0),
                LocalDateTime.of(2019, 5, 4, 14, 0), 100000);
        Assert.assertEquals(2, mealsBetween.size());
    }
}