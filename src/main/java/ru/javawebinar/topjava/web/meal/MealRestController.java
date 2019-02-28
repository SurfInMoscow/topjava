package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;
    private int userId = SecurityUtil.authUserId();

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
}
