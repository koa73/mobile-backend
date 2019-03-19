package ru.mobile.lib.repository;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *
 * Created by OAKutsenko on 13.12.2016.
 */

@Repository("templateCall")
public class TemplateCall {

    private JdbcTemplate jdbcTemplate;
    private final String SCHEMA = "public";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Map<String, Object> simpleTemplateCall(String funcName, SqlParameter[] parameters, SqlParameterSource value){

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(SCHEMA)
                .withFunctionName(funcName)
                .declareParameters(parameters);

        return call.execute(value);

    }
}
