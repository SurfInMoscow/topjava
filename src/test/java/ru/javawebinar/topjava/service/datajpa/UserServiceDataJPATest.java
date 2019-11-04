package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class UserServiceDataJPATest extends AbstractUserServiceTest {
    @Test
    public void testGetWithMeals() throws Exception {
        User user = userService.getWithMeals(USER_ID);
        assertMatch(user, USER);
        Assert.assertTrue(user.getMeals().size() != 0);
    }

    @Test
    public void testGetWithMealsNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        userService.getWithMeals(1);
    }
}