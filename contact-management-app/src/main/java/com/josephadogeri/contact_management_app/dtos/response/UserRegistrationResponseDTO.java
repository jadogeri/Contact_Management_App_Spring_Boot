package com.josephadogeri.contact_management_app.dtos.response;

import com.josephadogeri.contact_management_app.Auditable;

public class UserRegistrationResponseDTO extends Auditable {
    private String username;
    private String email;

    public UserRegistrationResponseDTO(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }
    public String getUsername() {  return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

}