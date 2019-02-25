package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealRestController {

    private MealService service;

    public Meal save(int userId, Meal meal) {
        return service.save(userId, meal);
    }

    public void delete(int usrId, int id) throws NotFoundException {
        //checkNotFoundWithId(service.delete(usrId, id), id); TODO
    }

    public Meal get(int usrId, int id) throws NotFoundException {
        return checkNotFoundWithId(service.get(usrId, id), id);
    }

    public void update(int usrId, Meal meal) {
        checkNotFoundWithId(service.save(usrId, meal), meal.getId());
    }

    public List<Meal> getAll(int usrId) {
        return service.getAll(usrId);
    }
}
