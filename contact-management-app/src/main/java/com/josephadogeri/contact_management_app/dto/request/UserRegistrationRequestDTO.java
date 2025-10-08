package com.josephadogeri.contact_management_app.dto.request;

import com.josephadogeri.contact_management_app.entity.User;
import com.josephadogeri.contact_management_app.interfaces.UserCreation;

import java.io.Serializable;

public class UserRegistrationRequestDTO {
    private String username;
    private String email;
    private String password;

    public UserRegistrationRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public String getUsername() {  return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

}