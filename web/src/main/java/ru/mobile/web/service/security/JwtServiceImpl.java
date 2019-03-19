package ru.mobile.web.service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.phone4pay.common.service.uuid.UUIDType5Impl;
import ru.phone4pay.ui.rest.model.LinkData;

import java.util.Date;

/**
 *
 * Created by oakutsenko on 13.04.2018.
 */

@Service
public class JwtServiceImpl implements JwtService {


    //private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${invoice.jwt.key}")
    private String key;

    @Value("${invoice.jwt.iss}")
    private String iss;

    @Autowired(required = false)
    UUIDType5Impl uuid;

    @Override
    public String getJwtToken(LinkData request, String phone, String cur) {

       final Date now = new Date();

        final String token =  Jwts.builder()
                .setId(uuid.getUUID(phone+now).toString())
                .setIssuedAt(now)
                .setSubject("fixed")
                .setIssuer(iss)
                .compressWith(CompressionCodecs.DEFLATE)
                .claim("receiver", phone)
                .claim("card_id", request.getCardId())
                .claim("text", request.getFixTxt()?request.getText():null)
                .claim("cur", cur)
                .claim("sum", request.getFixSum()?request.getSum():null)
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(key))
                .compact();
        return token;
    }
}
