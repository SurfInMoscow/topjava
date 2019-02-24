package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Override
    public Meal save(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    @Override
    public void delete(int usrId, int id) {

    }

    @Override
    public Meal get(int usrId, int id) {
        return null;
    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public List<Meal> getAll(int usrId) {
        return null;
    }
}
