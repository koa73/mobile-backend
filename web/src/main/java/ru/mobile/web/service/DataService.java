package ru.mobile.web.service;

import ru.phone4pay.ui.rest.model.Cards;
import ru.phone4pay.ui.rest.model.LinkData;

import java.util.List;

/**
 *
 * Created by oakutsenko on 16.03.2018.
 */
public interface DataService {

    String getOperations(String phone);
    List<Cards> listAllCards(String phone);
    String getUrl(LinkData request, String phone);
}
