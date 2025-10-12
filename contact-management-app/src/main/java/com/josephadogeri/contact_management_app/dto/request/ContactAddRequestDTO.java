package com.josephadogeri.contact_management_app.dto.request;

import com.josephadogeri.contact_management_app.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ContactAddRequestDTO{

    private String name;
    private String email;
    private String phone;

    public ContactAddRequestDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}