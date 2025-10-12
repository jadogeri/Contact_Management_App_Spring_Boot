package com.josephadogeri.contact_management_app.dtos.response;

public class UserLoginResponseDTO{

    private String accessToken;
    public UserLoginResponseDTO(String token) {

        this.accessToken = token;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}