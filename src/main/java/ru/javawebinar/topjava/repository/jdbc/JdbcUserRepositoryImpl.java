package ru.javawebinar.topjava.repository.jdbc;

import com.google.common.collect.Maps;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.time.zone.ZoneRules;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT OUTER JOIN user_roles ur on u.id = ur.user_id ORDER BY name, email",
                (ResultSetExtractor<List<User>>) rs -> {
                    Map<Integer, User> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        int id = Integer.parseInt(rs.getString("id"));
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        String password = rs.getString("password");
                        String registered = rs.getString("registered").replace(' ', 'T').substring(0,19);
                        Boolean enabled = rs.getBoolean("enabled");
                        int calories = Integer.parseInt(rs.getString("calories_per_day"));
                        int user_id = Integer.parseInt(rs.getString("user_id"));
                        String role = rs.getString("role");
                        Role r = role.equals("ROLE_ADMIN") ? Role.ROLE_ADMIN : Role.ROLE_USER;
                        LocalDateTime localDateTime = TimeUtil.stringToLocalDateTime(registered);
                        Date date = Date.from(TimeUtil.stringToLocalDateTime(registered).toLocalDate().atTime(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()).toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now())));
                        User user = new User(id, name, email, password, calories, enabled, date, Collections.singleton(r));
                        map.putIfAbsent(id, user);
                        map.get(id).getRoles().add(r);
                    }
                    return new ArrayList<>(map.values());
                });
    }
}
