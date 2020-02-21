package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class MealTo extends BaseTo {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    @JsonIgnore
    private final boolean excess;

    @ConstructorProperties({"id", "dateTime", "description", "calories", "excess"})
    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess, Integer id) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealTo mealTo = (MealTo) o;
        return calories == mealTo.calories &&
                excess == mealTo.excess &&
                Objects.equal(dateTime, mealTo.dateTime) &&
                Objects.equal(description, mealTo.description) &&
                Objects.equal(id, mealTo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dateTime, description, calories, excess, id);
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