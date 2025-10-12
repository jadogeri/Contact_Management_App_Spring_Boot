package com.josephadogeri.contact_management_app.dtos.response;

public class ContactDeleteSingleResponseDTO {

    private String message;

    public ContactDeleteSingleResponseDTO(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}