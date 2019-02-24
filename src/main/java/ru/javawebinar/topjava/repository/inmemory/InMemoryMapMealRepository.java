package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryMapMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> mealMap = new ConcurrentHashMap<>();

    @Override
    public Meal save(int userId, Meal meal) {
        mealMap.computeIfAbsent(userId, k -> new HashMap<>());
        mealMap.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int usrId, int id) {
        mealMap.get(usrId).remove(id);
    }

    @Override
    public Meal get(int usrId, int id) {
        return mealMap.get(usrId).get(id);
    }

    @Override
    public List<Meal> getAll(int usrId) {
        return mealMap.get(usrId).values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}
