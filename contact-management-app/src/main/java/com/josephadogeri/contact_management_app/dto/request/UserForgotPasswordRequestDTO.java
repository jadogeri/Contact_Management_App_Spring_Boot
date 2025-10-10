package com.josephadogeri.contact_management_app.dto.request;

import com.josephadogeri.contact_management_app.abstracts.EmailAddressBase;

public class UserForgotPasswordRequestDTO extends EmailAddressBase {

    public UserForgotPasswordRequestDTO(String email) {

        super(email);
    }

}