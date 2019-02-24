package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

public abstract class AbstractMealRepository<SK> implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractMealRepository.class.getName());

    protected abstract SK getSearchKey(Integer id);

    protected abstract void doUpdate(Meal meal, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(Meal meal, SK searchKey);

    protected abstract Meal doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract List<Meal> doCopyAll();

    @Override
    public void clear() {

    }

    @Override
    public void update(Meal meal) {
        LOG.info("Update " + meal);
        SK searchKey = null;
        try {
            searchKey = getExistedSearchKey(meal.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doUpdate(meal, searchKey);
    }

    @Override
    public void save(Meal meal) {
        LOG.info("Save " + meal);
        SK searchKey = null;
        try {
            searchKey = getNotExistedSearchKey(meal.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doSave(meal, searchKey);
    }

    @Override
    public Meal getMealByID(Integer id) {
        LOG.info("Save " + id);
        SK searchKey = null;
        try {
            searchKey = getExistedSearchKey(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return doGet(searchKey);
    }

    @Override
    public void delete(Integer id) {
        LOG.info("Delete " + id);
        SK searchKey = null;
        try {
            searchKey = getExistedSearchKey(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doDelete(searchKey);
    }

    @Override
    public List<Meal> getAll() {
        LOG.info("Get list of Meals");
        return doCopyAll();
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
    }
}
