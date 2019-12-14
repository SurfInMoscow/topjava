package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.TimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class JdbcUserOneToManyResultSetExtractor extends OneToManyResultSetExtractor<User, Role, Integer> {

    public JdbcUserOneToManyResultSetExtractor() {
        super(new UserMapper(), new RoleMapper());
    }

    @Override
    protected Integer mapPrimaryKey(ResultSet rs) throws SQLException {
        return rs.getInt("id");
    }

    @Override
    protected Integer mapForeignKey(ResultSet rs) throws SQLException {
        if (rs.getObject("user_id") == null) {
            return null;
        } else {
            return rs.getInt("user_id");
        }
    }

    @Override
    protected void addChild(User root, Role child) {
        if (root.getRoles() == null) {
            root.setRoles(new HashSet<>());
        }
        root.getRoles().add(child);
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRegistered(TimeUtil.parseLocalDateTimeToDate(rs.getString("registered").replace(' ', 'T').substring(0, 16)));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setCaloriesPerDay(rs.getInt("calories_per_day"));
            return user;
        }
    }

    private static class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("role").equals("ROLE_ADMIN") ? Role.ROLE_ADMIN : Role.ROLE_USER;
        }
    }
}
