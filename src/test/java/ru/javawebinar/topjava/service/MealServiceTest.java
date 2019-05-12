package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml",
                        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void save() throws Exception {
        Meal userLunch = new Meal(LocalDateTime.of(2019, 5, 4, 15, 0), "ланч", 500);
        Meal created = service.save(USER_ID, userLunch);
        created.setId(created.getId());
        assertMatch(service.getAll(USER_ID), userSupper, userLunch, userDinner, userBreakfast);
    };

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID, USER_MEAL1_ID);
        assertMatch(service.getAll(USER_ID), userSupper, userDinner);
    };

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(0, USER_MEAL1_ID-1);
    };

    @Test
    public void get() throws Exception {
        Meal meal = service.get(0, USER_MEAL3_ID);
        assertMatch(meal, userSupper);
    };

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(0, 1);
    };

    @Test
    public void update() throws Exception {
       Meal updated = new Meal(adminLunch);
       updated.setCalories(100);
       updated.setDescription("чай");
       service.update(ADMIN_ID, updated);
       assertMatch(service.get(ADMIN_ID, ADMIN_MEAL1_ID), updated);
    };

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updated = new Meal(LocalDateTime.of(2019, 5, 4, 15, 0), "чай", 100);
        updated.setId(USER_ID);
        service.update(ADMIN_ID, updated);
    };

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, userSupper, userDinner, userBreakfast);
    };
}