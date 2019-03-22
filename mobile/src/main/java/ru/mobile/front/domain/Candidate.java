package ru.mobile.front.domain;


public class Candidate {

    private String phone;
    private String password;
    private String email;
    private String regId;

    public Candidate(){}

    public Candidate(String phone, String email, String password, String regId){
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.regId = regId;
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

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
