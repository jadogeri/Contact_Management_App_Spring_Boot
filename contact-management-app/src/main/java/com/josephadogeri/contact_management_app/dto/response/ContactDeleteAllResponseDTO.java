package com.josephadogeri.contact_management_app.dto.response;

public class ContactDeleteAllResponseDTO {

    private String message;

    public ContactDeleteAllResponseDTO(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}