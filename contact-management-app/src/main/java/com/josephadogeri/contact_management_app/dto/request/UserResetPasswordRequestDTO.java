package com.josephadogeri.contact_management_app.dto.request;

public class UserResetPasswordRequestDTO {
    private String email;

    public UserResetPasswordRequestDTO(String email) {

        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {}

}