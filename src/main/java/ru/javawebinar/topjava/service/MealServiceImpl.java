package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {


    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheEvict(value = "meals", allEntries = true)
    public Meal save(int userId, Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(userId, meal);
    }

    @Override
    @CacheEvict(value = "meals", allEntries = true)
    public void delete(int usrId, int id) throws NotFoundException {
       checkNotFoundWithId(repository.delete(usrId, id), id);
    }

    @Override
    public Meal get(int usrId, int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(usrId, id), id);
    }

    @Override
    @CacheEvict(value = "meals", allEntries = true)
    public void update(int usrId, Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(repository.save(usrId, meal), meal.getId());
    }

    @Override
    @Cacheable("meals")
    public List<Meal> getAll(int usrId) {
        return repository.getAll(usrId);
    }

    @Override
    @Cacheable("meals")
    public List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int usrId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return repository.getBetween(startDateTime, endDateTime, usrId);
    }
}
