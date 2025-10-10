package com.josephadogeri.contact_management_app.abstracts;

import com.josephadogeri.contact_management_app.Auditable;

public abstract class PasswordBase{

    private String password;

    public PasswordBase(String password) {
        super();
        this.password = password;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

}
