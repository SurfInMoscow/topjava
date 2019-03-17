package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.TimeUtil.adjustEndDateTime;
import static ru.javawebinar.topjava.util.TimeUtil.adjustStartDateTime;

public interface MealService {
    Meal save(int userId, Meal meal);

    void delete(int usrId, int id) throws NotFoundException;

    Meal get(int usrId, int id) throws NotFoundException;

    void update(int usrId, Meal meal) throws NotFoundException;

    List<Meal> getAll(int usrId);

    List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int usrId);

    default List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return getBetweenDateTimes(adjustStartDateTime(startDate), adjustEndDateTime(endDate), userId);
    }
}
