package ru.mobile.front.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.mobile.lib.repository.TemplateCall;

import java.sql.Types;
import java.util.Map;

@Repository
public class WarehouseDaoImpl implements WarehouseDAO {

    @Autowired(required = false)
    private TemplateCall template;

    //private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String getTopics(int topic_id){

        Map<String, Object> out = template.simpleTemplateCall(
                "get_topic",
                new SqlParameter[] {
                        new SqlParameter("in_id", Types.SMALLINT),
                },
                new MapSqlParameterSource()
                        .addValue("in_id", topic_id));

        return out.get("returnvalue").toString();
    }
}
