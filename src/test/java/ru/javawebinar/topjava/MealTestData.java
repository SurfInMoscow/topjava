package ru.javawebinar.topjava;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL1_ID = START_SEQ + 2;
    public static final int USER_MEAL2_ID = START_SEQ + 3;
    public static final int USER_MEAL3_ID = START_SEQ +4;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 5;

    public static final Meal userBreakfast = new Meal(USER_MEAL1_ID, LocalDateTime.of(2019, 5, 4, 10, 0), "завтрак", 500);
    public static final Meal userDinner = new Meal(USER_MEAL2_ID, LocalDateTime.of(2019, 5, 4, 13, 0), "обед", 800);
    public static final Meal userSupper = new Meal(USER_MEAL3_ID, LocalDateTime.of(2019, 5, 4, 18, 0), "ужин", 800);
    public static final Meal adminLunch = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2019, 5, 4, 15, 0), "ланч", 1000);

    public static final List<Meal> MEALS = List.of(userSupper, userDinner, userBreakfast, adminLunch);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id", "user", "excess").isEqualTo(expected);
        //assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJsonArr(Meal... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Meal.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Meal expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Meal.class), expected);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userSupper);
        updated.setUser(UserTestData.USER);
        updated.setCalories(1000);
        return updated;
    }

    public static Meal getNew() {
        Meal created = new Meal(userBreakfast);
        created.setUser(UserTestData.USER);
        created.setDescription("created for T&T");
        return created;
    }
}