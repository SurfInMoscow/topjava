package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

public abstract class AbstractMealRepository<SK> implements MealRepository {
    /*private static final Logger LOG = LoggerFactory.getLogger(AbstractMealRepository.class.getName());

    protected abstract SK getSearchKey( Integer id);

    protected abstract void doUpdate(Integer userId, Meal meal, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(Integer userId, Meal meal, SK searchKey);

    protected abstract Meal doGet(Integer userId, SK searchKey);

    protected abstract void doDelete(Integer userId, SK searchKey);

    protected abstract List<Meal> doCopyAll(Integer userId);

    @Override
    public void clear() {

    }

    @Override
    public void update(Integer userId, Meal meal) {
        LOG.info("Update " + meal);
        SK searchKey = null;
        try {
            searchKey = getExistedSearchKey(meal.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (userId == meal.getId()) {
            doUpdate(userId, meal, searchKey);
        }
    }

    @Override
    public void save(Integer userId, Meal meal) {
        LOG.info("Save " + meal);
        SK searchKey = null;
        try {
            searchKey = getNotExistedSearchKey(meal.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (userId == meal.getId()) {
            doSave(userId, meal, searchKey);
        }
    }

    @Override
    public Meal getMealByID(Integer userId, Integer id) {
        LOG.info("Save " + id);
        SK searchKey = null;
        try {
            searchKey = getExistedSearchKey(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (userId == id) ? doGet(userId, searchKey) : null;
    }

    @Override
    public void delete(Integer userId, Integer id) {
        LOG.info("Delete " + id);
        SK searchKey = null;
        try {
            searchKey = getExistedSearchKey(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (userId == id) {
            doDelete(userId, searchKey);
        }
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        LOG.info("Get list of Meals");
        //if user is authorized
        return doCopyAll(userId);
    }

    private SK getExistedSearchKey(Integer id) throws InterruptedException {
        SK searchKey = getSearchKey(id);
        if (!isExist(searchKey)) {
           throw new InterruptedException();
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(Integer id) throws InterruptedException {
        SK searchKey = getSearchKey(id);
        if (isExist(searchKey)) {
            throw new InterruptedException();
        }
        return searchKey;
    }*/
}
