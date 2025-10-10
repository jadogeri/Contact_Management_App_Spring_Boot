package com.josephadogeri.contact_management_app.dto.response;

import com.josephadogeri.contact_management_app.abstracts.PasswordBase;

public class UserForgotPasswordResponseDTO extends PasswordBase {

    public UserForgotPasswordResponseDTO(String password) {
        super(password);
    }
}