package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;

public class MealTo {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    private Integer id;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess, Integer id) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                ", id=" + id +
                '}';
    }
}