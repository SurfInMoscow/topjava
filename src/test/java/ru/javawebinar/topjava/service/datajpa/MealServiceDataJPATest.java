package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class MealServiceDataJPATest extends AbstractMealServiceTest {
    @Test
    public void testMealWithUser() {
        Meal meal = mealService.get(100000, USER_MEAL3_ID);
        Assert.assertNotNull(meal.getUser().getName());
    }

    @Test
    public void testGetWithUserNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        mealService.get(100015, USER_MEAL1_ID);
    }
}