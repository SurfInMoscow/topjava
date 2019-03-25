package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMapMealRepository implements MealRepository {

    private Map<Integer, InMemoryBaseRepositoryImpl<Meal>> mealMap = new ConcurrentHashMap<>();

    @Override
    public Meal save(int userId, Meal meal) {
        InMemoryBaseRepositoryImpl<Meal> meals = mealMap.computeIfAbsent(userId, k -> new InMemoryBaseRepositoryImpl<>());
        return meals.save(meal);
    }

    @Override
    public boolean delete(int usrId, int id) {
        Meal m = get(usrId, id);
        InMemoryBaseRepositoryImpl<Meal> meals = mealMap.get(usrId);
        return meals != null && meals.delete(id);
    }

    @Override
    public Meal get(int usrId, int id) {
        InMemoryBaseRepositoryImpl<Meal> meals = mealMap.get(usrId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int usrId) {
        //mealMap.computeIfAbsent(usrId, k -> new HashMap<>());
        //return mealMap.get(usrId).values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        return getAllFiltered(usrId, meal -> true);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int usrId) {
        return getAllFiltered(usrId, meal -> Util.isBetween(meal.getDateTime(), startDate, endDate));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        InMemoryBaseRepositoryImpl<Meal> meals = mealMap.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}
