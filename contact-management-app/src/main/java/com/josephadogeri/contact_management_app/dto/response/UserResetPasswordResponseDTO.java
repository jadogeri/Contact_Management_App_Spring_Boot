package com.josephadogeri.contact_management_app.dto.response;

import com.josephadogeri.contact_management_app.abstracts.EmailAddressBase;
import com.josephadogeri.contact_management_app.abstracts.PasswordBase;

public class UserResetPasswordResponseDTO extends PasswordBase {

    public UserResetPasswordResponseDTO(String password) {
        super(password);
    }
}