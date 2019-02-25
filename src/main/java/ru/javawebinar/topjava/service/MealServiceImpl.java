package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Override
    public Meal save(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    @Override
    public void delete(int usrId, int id) throws NotFoundException {
       checkNotFoundWithId(repository.delete(usrId, id), id);
    }

    @Override
    public Meal get(int usrId, int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(usrId, id), id);
    }

    @Override
    public void update(int usrId, Meal meal) {
        checkNotFoundWithId(repository.save(usrId, meal), meal.getId());
    }

    @Override
    public List<Meal> getAll(int usrId) {
        return repository.getAll(usrId);
    }
}
