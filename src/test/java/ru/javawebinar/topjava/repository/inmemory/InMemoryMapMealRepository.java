package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMapMealRepository implements MealRepository {
    private final static Logger log = LoggerFactory.getLogger(InMemoryMapMealRepository.class);

    private Map<Integer, InMemoryBaseRepositoryImpl<Meal>> mealMap = new ConcurrentHashMap<>();

    @Override
    public Meal save(int userId, Meal meal) {
        Objects.requireNonNull(meal, "meal must not be null");
        InMemoryBaseRepositoryImpl<Meal> meals = mealMap.computeIfAbsent(userId, k -> new InMemoryBaseRepositoryImpl<>());
        return meals.save(meal);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ postConstruct");
    }

    @PreDestroy
    public void preDestriy() {
        log.info("+++ preDestroy");
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
        return getAllFiltered(usrId, meal -> true);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int usrId) {
        Objects.requireNonNull(startDate, "startDateTime must not be null");
        Objects.requireNonNull(endDate, "endDateTime must not be null");
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
