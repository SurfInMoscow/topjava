package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    Meal save(int userId, Meal meal);

    void delete(int usrId, int id);

    Meal get(int usrId, int id);

    void update(int usrId, Meal meal);

    List<Meal> getAll(int usrId);
}
