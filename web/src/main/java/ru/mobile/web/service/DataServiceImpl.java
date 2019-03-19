package ru.mobile.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.phone4pay.ui.repository.UserDAO;
import ru.phone4pay.ui.rest.model.Cards;
import ru.phone4pay.ui.rest.model.LinkData;
import ru.phone4pay.ui.service.security.JwtService;

import java.util.List;

/**
 *
 * Created by oakutsenko on 16.03.2018.
 */

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    UserDAO repository;

    @Autowired
    JwtService jwtService;

    @Value("${invoice.jwt.url}")
    private String url;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String getUrl(LinkData request, String phone) {

        try {

            String response = "{\"url\":\""+url+"?";
            if (!request.getFixSum()){
                response = response + "sum=" +((request.getSum() == null)?"":request.getSum())+"&";
            }
            if (!request.getFixTxt()){
                response = response + "text=" +request.getText()+"&";
            }

            return response+"token="+jwtService.getJwtToken(request, phone,"643")+"\"}";

        } catch (Exception e){
            log.error(e.getMessage()+" ,"+e.getCause());
        }
        return null;
    }

    @Override
    public List<Cards> listAllCards(String phone) {

        return repository.getCarList(phone);
    }

    @Override
    public String getOperations(String phone) {

        return repository.getOperation(phone);
    }
}
