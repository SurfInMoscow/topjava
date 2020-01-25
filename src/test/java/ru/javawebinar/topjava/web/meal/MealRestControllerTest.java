package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    private MealService mealService;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArr(userSupper, userDinner, userBreakfast))
                .andDo(print());
        ;
    }

    @Test
    void get() throws Exception {
        Meal supper = userSupper;
        supper.setUser(USER);
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + USER_MEAL3_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(supper));
    }

    @Test
    void getBetween() throws Exception {
        List<Meal> meals = TestUtil.readListFromJsonMvcResult(
                mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter" + "?startDate=2019-05-04&startTime=17:30:00&endDate=2019-05-04&endTime=18:01:01"))
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(contentJsonArr(userSupper))
                        .andReturn(), Meal.class);
        Assertions.assertEquals(1, meals.size());
    }

    @Test
    void filterAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter" + "?startDate=&endTime="))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(contentJsonArr(userSupper, userDinner, userBreakfast));
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + USER_MEAL3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(USER_ID, USER_MEAL3_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());
        Meal created = TestUtil.readFromJson(actions, Meal.class);
        assertMatch(created, newMeal);
        assertMatch(created, mealService.get(USER_ID, created.getId()));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + USER_MEAL3_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        Assertions.assertThrows(NotFoundException.class, () -> mealService.get(USER_ID, USER_MEAL3_ID));
    }
}