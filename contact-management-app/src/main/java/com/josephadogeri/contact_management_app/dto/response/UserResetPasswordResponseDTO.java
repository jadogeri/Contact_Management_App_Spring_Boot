package com.josephadogeri.contact_management_app.dto.response;

import com.josephadogeri.contact_management_app.abstracts.EmailAddressBase;
import com.josephadogeri.contact_management_app.abstracts.PasswordBase;

public class UserResetPasswordResponseDTO {

    private String message;

    public UserResetPasswordResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}