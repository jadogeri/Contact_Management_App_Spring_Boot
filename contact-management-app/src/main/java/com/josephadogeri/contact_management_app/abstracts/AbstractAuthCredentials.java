package com.josephadogeri.contact_management_app.abstracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AbstractAuthCredentials
{
    @Schema(description = "Username of the new user", example = "johndoe1234@")
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String username;
    @Schema(description = "Password must contain at least one letter, one number and one special character")
    @Size(min = 8, max = 32)
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?^&])[A-Za-z\\d@$!%*#?^&]{3,}$")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String password;
    public AbstractAuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
