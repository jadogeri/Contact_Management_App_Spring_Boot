package com.josephadogeri.contact_management_app.utils;

import com.josephadogeri.contact_management_app.interfaces.Validatable;

public class CredentialsValidatorUtil implements Validatable {

    @Override
    public boolean isValidEmail(String email){
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean isValidPassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean isValidUsername(String username){
        return username.matches(USERNAME_REGEX);
    }

}
