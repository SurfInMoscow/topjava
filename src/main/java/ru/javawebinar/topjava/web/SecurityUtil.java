package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int usrId = AbstractBaseEntity.START_SEQ;

    public static int authUserId() {
        return usrId;
    }

    public static void setAuthUserId(int usrId) {
        SecurityUtil.usrId = usrId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
