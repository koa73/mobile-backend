package ru.mobile.oauth.repository;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.mobile.oauth.domain.Candidate;
import ru.mobile.oauth.domain.PwdChange;
import ru.mobile.oauth.domain.User;
import ru.mobile.oauth.domain.UserRowMapper;

import java.sql.Types;
import java.util.Map;

/**
 *
 * Created by OAKutsenko on 19.09.2016.
 */


@Component("userData")
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private final String SCHEMA = "public";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void changePassword(PwdChange request) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(SCHEMA)
                .withFunctionName("change_password")
                .declareParameters(request.getSqlParameters());

        call.execute(request.getCallValue());
    }

    @Override
    public void resetTryCount(String uid) {

        final String SQL = "UPDATE users SET trycount = 0, lastlogin = now() WHERE uid = ?::uuid;";
        jdbcTemplate.update(SQL, uid);
    }

    @Override
    public void setTryCount(String uid) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(SCHEMA)
                .withFunctionName("set_login_try")
                .declareParameters(new SqlParameter("in_uid", Types.OTHER));

        call.execute(new MapSqlParameterSource("in_uid", uid));
    }

    @Override
    public User getUserDetail(String uid) throws UsernameNotFoundException {

        try {

            final String SQL_USER_DATA = "SELECT uid, phone, password, enabled, trycount  FROM users WHERE uid = ?::uuid";

            return  (User) jdbcTemplate.queryForObject(
                    SQL_USER_DATA, new Object[]{uid}, new UserRowMapper());

        } catch (Exception e){

            throw new UsernameNotFoundException("User "+uid+" not found. @103");
        }

    }

    @Override
    public String createUser(Candidate candidate) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(SCHEMA)
                .withFunctionName("create_user")
                .withReturnValue()
                .declareParameters( candidate.getSqlParameters()
                );
        Map<String, Object> out = jdbcCall.execute(candidate.getCallValue());

        return out.get("returnvalue").toString();
    }
}
