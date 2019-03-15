package ru.mobile.oauth.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by OAKutsenko on 20.09.2016.
 */
public class UserRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();
        user.setUsername(resultSet.getString("uid"));
        user.setPhone(resultSet.getString("phone"));
        user.setPassword(resultSet.getString("password"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setTryCount(resultSet.getInt("trycount"));
        return user;
    }
}
