package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;

    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    public Meal save(int userId, Meal meal) {
        meal.setUser(crudUserRepository.findById(userId).get());
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int usrId, int id) {
        return crudMealRepository.delete(usrId, id) != 0;
    }

    @Override
    public Meal get(int usrId, int id) {
        return crudMealRepository.findByUser_IdAndId(usrId, id);
    }

    @Override
    public List<Meal> getAll(int usrId) {
        return crudMealRepository.findAllByUser_IdOrderByDateTimeDesc(usrId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int usrId) {
        return crudMealRepository.findAllByDateTimeBetweenAndUser_Id(startDate, endDate, usrId);
    }
}
