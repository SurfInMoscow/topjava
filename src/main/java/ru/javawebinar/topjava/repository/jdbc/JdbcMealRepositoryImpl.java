package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    @Override
    public Meal save(int userId, Meal meal) {
        return null;
    }

    @Override
    public boolean delete(int usrId, int id) {
        return false;
    }

    @Override
    public Meal get(int usrId, int id) {
        return null;
    }

    @Override
    public List<Meal> getAll(int usrId) {
        return null;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int usrId) {
        return null;
    }
}
