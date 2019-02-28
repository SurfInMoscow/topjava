package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryMapMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMapMealRepository.class);

    private Map<Integer, Map<Integer, Meal>> mealMap = new ConcurrentHashMap<>();

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        mealMap.computeIfAbsent(userId, k -> new HashMap<>());
        mealMap.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int usrId, int id) {
        log.info("delete {}", id);
        Meal m = get(usrId, id);
        return mealMap.get(usrId).remove(id, m);
    }

    @Override
    public Meal get(int usrId, int id) {
        log.info("get {}", id);
        return mealMap.get(usrId).get(id);
    }

    @Override
    public List<Meal> getAll(int usrId) {
        log.info("getAll");
        return mealMap.get(usrId).values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}
