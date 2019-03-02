package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;
    private int userId = SecurityUtil.authUserId();
    private static final LocalTime startTime = LocalTime.of(7, 0);
    private static final LocalTime endTime = LocalTime.of(21, 0);

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        log.info("save {}", meal);
        return service.save(userId, meal);
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete {}", id);
        service.delete(userId, id);
    }

    public Meal get(int id) throws NotFoundException {
        log.info("get {}", id);
        return service.get(userId, id);
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, userId);
        service.save(userId, meal);
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(userId);
    }

    public List<MealTo> getFilteredWithExcessByCycle(List<Meal> meals, int caloriesPerDay) {
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExcess.add(createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExcess;
    }

    private static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }
}
