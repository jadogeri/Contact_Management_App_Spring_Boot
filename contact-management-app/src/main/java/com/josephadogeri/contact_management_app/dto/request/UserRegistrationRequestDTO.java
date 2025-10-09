package com.josephadogeri.contact_management_app.dto.request;


import com.josephadogeri.contact_management_app.abstracts.AbstractAuthCredentials;


public class UserRegistrationRequestDTO extends AbstractAuthCredentials {

    private String email;

    public UserRegistrationRequestDTO(String username, String email, String password) {
        super(username, password);
        this.email = email;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }


}