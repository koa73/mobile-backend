package ru.mobile.lib.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by OAKutsenko on 20.01.2017.
 */
public class OAuth2AuthoritiesExtractor implements AuthoritiesExtractor {

    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String AUTHORITIES = "authorities";

    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {

        String authorities = "ROLE_APP";
        if (map.containsKey(AUTHORITIES)) {
            authorities = asAuthorities(map.get(AUTHORITIES));
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }

    @SuppressWarnings("unchecked")
    private String asAuthorities(Object object) {
        if (object instanceof Collection) {
            return (String) ((Collection) object).stream().map(o -> {
                if (o instanceof Map) {
                    return ((Map) o).values().stream().collect(Collectors.joining(","));
                }
                return o.toString();
            }).collect(Collectors.joining(","));

        }
        if (ObjectUtils.isArray(object)) {
            return StringUtils.arrayToCommaDelimitedString((Object[]) object);
        }
        return object.toString();
    }
}
