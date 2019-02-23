package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void clear();

    void update(Meal meal);

    void save(Meal meal);

    Meal getMealByID(Integer id);

    void delete(Integer id);

    List<Meal> getAll();

    int size();
}
