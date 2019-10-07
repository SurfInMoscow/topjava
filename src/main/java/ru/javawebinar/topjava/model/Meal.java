package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;
import ru.javawebinar.topjava.util.Counter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = "deleteMeal", query = "DELETE FROM Meal m WHERE m.id=?1 AND m.user.id=?2"),
        @NamedQuery(name = "getMeal", query = "SELECT m FROM Meal m WHERE m.id=?1 AND m.user.id=?2"),
        @NamedQuery(name = "getMeals", query = "SELECT m FROM Meal m WHERE m.user.id=?1")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id_m", "dateTime"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {

    @NotNull
    @Column(name = "dateTime", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime dateTime;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Range(min = 10, max = 5000)
    @Column(name = "calories", nullable = false)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_m")
    private User user;

    //private int userId;

    public Meal() {
    }

    public Meal(Meal meal) {
        this(meal.id, meal.dateTime, meal.getDescription(), meal.getCalories());
    }

    public Meal(LocalDateTime dateTime, String description, int calories, int userId) {
        super(Counter.getIncrement());
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        //this.userId = userId;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    /*public int getUserId() {
        return userId;
    }*/

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                //", userId=" + //userId +
                ", id=" + id +
                '}';
    }
}
