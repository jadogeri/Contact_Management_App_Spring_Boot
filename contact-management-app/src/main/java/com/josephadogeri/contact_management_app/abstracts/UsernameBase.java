package com.josephadogeri.contact_management_app.abstracts;

public abstract class UsernameBase {

    private String username;

    public UsernameBase(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
