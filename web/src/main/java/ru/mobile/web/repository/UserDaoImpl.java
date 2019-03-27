package ru.mobile.web.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.mobile.lib.repository.TemplateCall;

import java.sql.Types;
import java.util.Map;

/**
 *
 * Created by oakutsenko on 21.03.2018.
 */

@Repository
public class UserDaoImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    //private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private TemplateCall template;

    @Override
    public Map<String, Object> getUserProfile(String phone) {

        return template.simpleTemplateCall(
                "get_user_info",
                new SqlParameter[] {
                        new SqlParameter("in_phone", Types.VARCHAR),
                },
                new MapSqlParameterSource()
                        .addValue("in_phone", phone)
        );
    }
}
