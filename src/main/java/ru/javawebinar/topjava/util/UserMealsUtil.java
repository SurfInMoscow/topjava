package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds;
        Map<String, Integer> map = new HashMap<>();
        userMealWithExceeds = mealList.stream().map(p -> {
            boolean exceed = false;
            if (TimeUtil.isBetween(p.getDateTime().toLocalTime(), startTime, endTime)) {
                if (map.containsKey(TimeUtil.toLocalDate(p.getDateTime()).toString())) {
                    int countCalories = map.get(TimeUtil.toLocalDate(p.getDateTime()).toString()) + p.getCalories();
                    if (countCalories > caloriesPerDay) {
                        exceed = true;
                    }
                    map.put(TimeUtil.toLocalDate(p.getDateTime()).toString(), countCalories);
                } else {
                    map.put(TimeUtil.toLocalDate(p.getDateTime()).toString(), p.getCalories());
                    if (p.getCalories() > caloriesPerDay) {
                        exceed = true;
                    }
                }
                return new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), exceed);
            }
            return null;
        }).collect(Collectors.toList()).stream().filter(Objects::nonNull).collect(Collectors.toList());
        return userMealWithExceeds;
    }
}
