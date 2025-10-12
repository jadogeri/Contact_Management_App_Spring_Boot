package com.josephadogeri.contact_management_app.dtos.response;

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