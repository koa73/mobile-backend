package ru.mobile.front.rest.model;

import org.hibernate.validator.constraints.NotEmpty;
import ru.mobile.front.domain.Candidate;


/**
 *
 * Created by OAKutsenko on 22.07.2017.
 */

public class UserCreateReq extends Candidate {

    private String cmdCode;

    @NotEmpty(message = "credentials")
    private String credentials;

    public UserCreateReq() {
        super();
    }

    public UserCreateReq(String phone, String email, String password, String regId) {
        super(phone, email, password, regId);
    }

    public String getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(String  cmdCode) {
        this.cmdCode = cmdCode;
    }


    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
