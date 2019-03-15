package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {

    Meal save(int userId, Meal meal);

    boolean delete(int usrId, int id);

    Meal get(int usrId, int id);

    List<Meal> getAll(int usrId);

    List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int usrId);
}
