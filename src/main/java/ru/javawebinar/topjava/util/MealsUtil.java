package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMapMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealTo> getWithExcess(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> true);
    }

    /*public static final int DEFAULT_CALORIES_PER_DAY = 2000;
    public static List<MealTo> mealsWithExcess;
    public static List<Meal> meals;
    public static MealRepository mealRepository = new InMemoryMapMealRepository();
    public static User user = new User(1,"Pavel", "p.vorobyev@inbox.ru", "12345", Role.ROLE_ADMIN);

    static {

      *//*  mealRepository.save(user.getId(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealRepository.save(user.getId(),new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealRepository.save(user.getId(),new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealRepository.save(user.getId(),new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealRepository.save(user.getId(),new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealRepository.save(user.getId(),new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );*//*
        //mealsWithExcess = getFilteredWithExcessByCycle(meals, LocalTime.of(7, 0), LocalTime.of(21, 0), 2000);
        mealsWithExcess = getFilteredWithExcessByCycle(mealRepository.getAll(user.getId()), LocalTime.of(7, 0), LocalTime.of(21, 0), 2000);
    }

    public static void main(String[] args) {
       *//* List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );*//*
        //mealsWithExcess = getFilteredWithExcess(meals, LocalTime.of(7, 0), LocalTime.of(21, 0), 2000);
        mealsWithExcess.forEach(System.out::println);

        System.out.println(getFilteredWithExcessByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExcessInOnePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        //System.out.println(getFilteredWithExcessInOnePass2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println("!!!" + mealsWithExcess);
        mealRepository.getAll(user.getId()).forEach(meal -> System.out.println(meal.getDateTime().toString()));
    }

    *//*public static List<MealTo> getFilteredWithExcess(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }*//*

    public static List<MealTo> getFilteredWithExcessByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExcess.add(createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExcess;
    }

    public static List<MealTo> getFilteredWithExcessInOnePass(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<List<Meal>> list = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate)).values();

        return list.stream().flatMap(dayMeals -> {
            boolean excess = dayMeals.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
            return dayMeals.stream().filter(meal ->
                    TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                    .map(meal -> createWithExcess(meal, excess));
        }).collect(toList());
    }

   *//* public static List<MealTo> getFilteredWithExcessInOnePass2(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class Aggregate {
            private final List<Meal> dailyMeals = new ArrayList<>();
            private int dailySumOfCalories;

            private void accumulate(Meal meal) {
                dailySumOfCalories += meal.getCalories();
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    dailyMeals.add(meal);
                }
            }

            // never invoked if the upstream is sequential
            private Aggregate combine(Aggregate that) {
                this.dailySumOfCalories += that.dailySumOfCalories;
                this.dailyMeals.addAll(that.dailyMeals);
                return this;
            }

            private Stream<MealTo> finisher() {
                final boolean excess = dailySumOfCalories > caloriesPerDay;
                return dailyMeals.stream().map(meal -> createWithExcess(meal, excess));
            }
        }

        Collection<Stream<MealTo>> values = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate,
                        Collector.of(Aggregate::new, Aggregate::accumulate, Aggregate::combine, Aggregate::finisher))
                ).values();

        return values.stream().flatMap(identity()).collect(toList());
    }*/

    public static MealTo createWithExcess(Meal meal, boolean excess) {
        //return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }

    private static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> Util.isBetween(meal.getTime(), startTime, endTime));
    }
}