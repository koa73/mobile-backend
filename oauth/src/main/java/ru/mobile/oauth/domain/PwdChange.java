package ru.mobile.oauth.domain;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.Types;

public class PwdChange {

    private String user;
    private String password;
    private String newPassword;

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public SqlParameterSource getCallValue() {
        return new MapSqlParameterSource()
                .addValue("in_uid", this.user)
                .addValue("in_password", this.newPassword);
    }


    public SqlParameter[] getSqlParameters() {
        return new SqlParameter[] {
                new SqlParameter("in_uid", Types.OTHER),
                new SqlParameter("in_password", Types.VARCHAR),
        };
    }
}
