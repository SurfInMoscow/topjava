package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setUser(em.find(User.class, userId));
            em.persist(meal);
            return meal;
        } else {
            meal.setUser(em.find(User.class, userId));
            em.merge(meal);
            return meal;
        }
    }

    @Override
    @Transactional
    public boolean delete(int usrId, int id) {
        return em.createNamedQuery("deleteMeal").setParameter(1, id)
                .setParameter(2, usrId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int usrId, int id) {
        return em.createNamedQuery("getMeal", Meal.class).setParameter(1, id)
                .setParameter(2, usrId).getSingleResult();
    }

    @Override
    public List<Meal> getAll(int usrId) {
        return em.createNamedQuery("getMeals", Meal.class).setParameter(1, usrId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int usrId) {
        return em.createNamedQuery("getMealsBetween", Meal.class).setParameter(1, usrId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();
    }
}
