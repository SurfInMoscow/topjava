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

    /**
     * https://stackoverflow.com/questions/5482141/what-is-the-difference-between-entitymanager-find-and-entitymanger-getreferenc
     * use getReference() instead of find()
     */
    @Override
    @Transactional
    public Meal save(int userId, Meal meal) {
        meal.setUser(em.find(User.class, userId));
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
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
        /*return em.createNamedQuery("getMeal", Meal.class).setParameter(1, id)
                .setParameter(2, usrId).getSingleResult();*/
        Meal meal = em.find(Meal.class, id);
        return meal != null && meal.getUser().getId() == usrId ? meal : null;
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
