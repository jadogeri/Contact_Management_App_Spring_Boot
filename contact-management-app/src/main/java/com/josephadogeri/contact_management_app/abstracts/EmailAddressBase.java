package com.josephadogeri.contact_management_app.abstracts;

public abstract class EmailAddressBase {

    private String email;

    public EmailAddressBase(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
