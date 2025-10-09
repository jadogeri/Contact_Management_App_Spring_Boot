package com.josephadogeri.contact_management_app.abstracts;

public abstract class PasswordBase {

    private String password;

    public PasswordBase(String password) {
        this.password = password;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {}

}
