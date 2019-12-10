package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.TimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class JdbcUserSetExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, User> map = new LinkedHashMap<>();
        while (rs.next()) {
            int usrId = Integer.parseInt(rs.getString("id"));
            String name = rs.getString("name");
            String usr_email = rs.getString("email");
            String password = rs.getString("password");
            Date registered = TimeUtil.parseLocalDateTimeToDate(rs.getString("registered").replace(' ', 'T').substring(0, 16));
            Boolean enabled = rs.getBoolean("enabled");
            int calories = Integer.parseInt(rs.getString("calories_per_day"));
            int user_id = Integer.parseInt(rs.getString("user_id"));
            Role r = rs.getString("role").equals("ROLE_ADMIN") ? Role.ROLE_ADMIN : Role.ROLE_USER;
            User user = new User(usrId, name, usr_email, password, calories, enabled, registered, Collections.singleton(r));
            map.putIfAbsent(usrId, user);
            map.get(usrId).getRoles().add(r);
        }
        return new ArrayList<>(map.values());
    }
}
