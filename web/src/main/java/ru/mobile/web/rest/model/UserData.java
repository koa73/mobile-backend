package ru.mobile.web.rest.model;

import ru.mobile.lib.rest.validation.Phone;

import javax.validation.constraints.NotNull;

/**
 *
 * Created by OAKutsenko on 02.02.2017.
 */

public class UserData {

    @Phone
    private String phone;

    @NotNull(message = "credentials")
    private String credentials;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
