package com.josephadogeri.contact_management_app.dto.response;

public class StatusResponseDTO
{
    private String message;

    public StatusResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}