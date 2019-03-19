package ru.mobile.web.service.security;

import ru.phone4pay.ui.rest.model.LinkData;

/**
 *
 * Created by oakutsenko on 13.04.2018.
 */

public interface JwtService {

    String getJwtToken(LinkData request, String phone, String cur);
}
