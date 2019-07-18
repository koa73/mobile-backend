package ru.mobile.front.rest.model;

import org.hibernate.validator.constraints.NotEmpty;
import ru.mobile.front.domain.Candidate;


/**
 *
 * Created by OAKutsenko on 22.07.2017.
 */

public class UserCreateReq extends Candidate {


    @NotEmpty(message = "credential")
    private String credential;

    public UserCreateReq() {
        super();
    }

    public UserCreateReq(String phone, String email, String password) {
        super(phone, email, password);
    }


    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
