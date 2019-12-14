package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    //private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final JdbcUserSetExtractor jdbcUserSetExtractor;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcUserSetExtractor jdbcUserSetExtractor) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcUserSetExtractor = jdbcUserSetExtractor;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            batchUpdateRoles(user);
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled,  calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
            deleteRoles(user);
            batchUpdateRoles(user);
            return user;
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
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN USER_ROLES on u.ID = USER_ROLES.USER_ID WHERE u.id=? ", jdbcUserSetExtractor, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u left join user_roles ur on u.ID = ur.USER_ID WHERE email=?", jdbcUserSetExtractor, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        //return jdbcTemplate.query("SELECT * FROM users u LEFT OUTER JOIN user_roles ur on u.id = ur.user_id ORDER BY name, email", jdbcUserSetExtractor);
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles on users.id = user_roles.user_id ORDER BY users.name, users.email", new JdbcUserOneToManyResultSetExtractor());
    }

    private void batchUpdateRoles(User user) {
        Set<Role> userRoles = user.getRoles();
        jdbcTemplate.batchUpdate("INSERT INTO USER_ROLES (USER_ID, ROLE) VALUES (?, ?)", userRoles, userRoles.size(), (ps, argument) -> {
            ps.setInt(1, user.getId());
            ps.setString(2, argument.toString());
        });
    }

    private void deleteRoles(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
    }
}
