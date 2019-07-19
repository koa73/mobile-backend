package ru.mobile.front.domain;


import ru.mobile.lib.rest.validation.Phone;

public class Candidate {

    @Phone
    private String phone;
    private String password;
    private String email;

    public Candidate(){}

    public Candidate(String phone, String email, String password){
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
