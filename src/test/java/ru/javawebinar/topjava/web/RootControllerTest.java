package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;

public class RootControllerTest extends AbstractControllerTest {
    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                /*.andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )))*/;
    }

    @Test
    public void getMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", new AssertionMatcher<List<Meal>>() {
                    @Override
                    public void assertion(List<Meal> meals) throws AssertionError {
                        assertMatch(meals, userSupper, userDinner, userBreakfast);
                    }
                }));
    }
}