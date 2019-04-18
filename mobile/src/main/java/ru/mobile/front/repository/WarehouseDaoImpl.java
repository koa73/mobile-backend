package ru.mobile.front.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.mobile.front.rest.view.TopicsView;
import ru.mobile.lib.repository.TemplateCall;
import ru.mobile.lib.rest.exception.RestApiException;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class WarehouseDaoImpl implements WarehouseDAO {

    @Autowired(required = false)
    private TemplateCall template;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<TopicsView> getTopics(int topic_id) throws RestApiException {

        Map<String, Object> out = template.simpleTemplateCall(
                "get_topic",
                new SqlParameter[] {
                        new SqlParameter("in_id", Types.SMALLINT),
                },
                new MapSqlParameterSource()
                        .addValue("in_id", topic_id));

        log.error("--------------> \n"+out.get("returnvalue").toString());

        return null;
    }
}
