package ru.javawebinar.topjava.service.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL1_ID;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL3_ID;
import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class MealServiceDataJPATest extends AbstractMealServiceTest {
    @Test
    public void testMealWithUser() {
        Meal meal = mealService.get(100000, USER_MEAL3_ID);
        assertNotNull(meal.getUser().getName());
    }

    @Test
    public void testGetWithUserNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> mealService.get(100015, USER_MEAL1_ID));
    }
}