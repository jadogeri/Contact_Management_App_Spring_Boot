package com.josephadogeri.contact_management_app.dtos.request;

import com.josephadogeri.contact_management_app.abstracts.AbstractAuthCredentials;

public class UserLoginRequestDTO extends AbstractAuthCredentials {

    public UserLoginRequestDTO(String username, String password) {

        super(username, password);
    }
}