package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(int id);

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

    }

    @Override
    public void save(Meal meal) {

    }

    @Override
    public Meal getMealByID(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Meal> getAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    private SK getExistedSearchKey(int id) throws InterruptedException {
        SK searchKey = getSearchKey(id);
        if (!isExist(searchKey)) {
           throw new InterruptedException();
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(int id) throws InterruptedException {
        SK searchKey = getSearchKey(id);
        if (isExist(searchKey)) {
            throw new InterruptedException();
        }
        return searchKey;
    }
}
