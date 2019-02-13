package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<Meal> {
    private Map<Integer, Meal> mealMap = new HashMap<>();

    @Override
    protected Meal getSearchKey(int id) {
        return mealMap.get(id);
    }

    @Override
    protected void doUpdate(Meal m, Meal meal) {
        mealMap.put(m.getId(), m);
    }

    @Override
    protected boolean isExist(Meal meal) {
        return meal != null;
    }

    @Override
    protected void doSave(Meal m, Meal meal) {
        mealMap.put(m.getId(), m);
    }

    @Override
    protected Meal doGet(Meal meal) {
        return meal;
    }

    @Override
    protected void doDelete(Meal meal) {
        mealMap.remove(meal.getId());
    }

    @Override
    protected List<Meal> doCopyAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void clear() {
        mealMap.clear();
    }

    @Override
    public int size() {
        return mealMap.size();
    }
}
