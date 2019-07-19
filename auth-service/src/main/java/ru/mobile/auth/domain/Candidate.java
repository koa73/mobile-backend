package ru.mobile.auth.domain;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.Types;

/**
 *
 * Created by OAKutsenko on 11.10.2016.
 */
public class Candidate {

    private String phone;

    private String email;

    private String password;

    private String credential;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SqlParameterSource getCallValue(){
        return new MapSqlParameterSource()
                .addValue("in_phone", this.phone)
                .addValue("in_password", this.password)
                .addValue("in_email", this.email);
    }

    public SqlParameter[] getSqlParameters(){

        return new SqlParameter[] {
                new SqlParameter("in_phone", Types.VARCHAR),
                new SqlParameter("in_password", Types.VARCHAR),
                new SqlParameter("in_email", Types.VARCHAR)
        };
    }
}
