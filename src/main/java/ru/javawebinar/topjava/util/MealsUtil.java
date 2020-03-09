package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

public class MealsUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    private MealsUtil() {
    }

    public static List<MealTo> getWithExcess(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> true);
    }

    public static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }

    private static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> Util.isBetween(meal.getTime(), startTime, endTime));
    }

    public static Meal updateFromTo(Meal meal, MealTo mealTo) {
        meal.setDescription(mealTo.getDescription());
        meal.setCalories(mealTo.getCalories());
        meal.setDateTime(mealTo.getDateTime());
        return meal;
    }

    public static Meal createNewFromTo(MealTo mealTo) {
        return new Meal(null, mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories());
    }
}