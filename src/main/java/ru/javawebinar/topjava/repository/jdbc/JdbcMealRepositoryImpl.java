package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Meal save(int userId, Meal meal) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("user_id_m", userId);
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("UPDATE meals SET datetime=:datetime, description=:description, " +
                                                   "calories=:calories, user_id_m=:user_id_m WHERE id=:id AND user_id_m=:user_id_m", map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int usrId, int id) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id_m=?", id, usrId) != 0;
    }

    @Override
    public Meal get(int usrId, int id) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id_m=?", ROW_MAPPER, id, usrId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int usrId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id_m=? ORDER BY dateTime DESC", ROW_MAPPER, usrId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int usrId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id_m=? AND dateTime BETWEEN ? AND ? ORDER BY datetime DESC",
                                                    ROW_MAPPER, usrId, startDate, endDate);
    }
}