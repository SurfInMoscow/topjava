package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Meal m where m.user.id=?1 and m.id=?2")
    int delete(int user_id_m, int id);

    Meal findByUser_IdAndId(int user_id_m, int id);

    List<Meal> findAllByUser_IdOrderByDateTimeDesc(int user_id_m);

    List<Meal> findAllByDateTimeBetweenAndUser_Id(LocalDateTime startDate, LocalDateTime endDate, int user_id_m);
}
