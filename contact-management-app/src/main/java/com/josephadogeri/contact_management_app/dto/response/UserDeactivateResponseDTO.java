package com.josephadogeri.contact_management_app.dto.response;

import com.josephadogeri.contact_management_app.abstracts.AbstractAuthCredentials;

public class UserDeactivateResponseDTO extends AbstractAuthCredentials {

    private boolean confirm;

    public UserDeactivateResponseDTO(String username, String password, boolean confirm) {

        super(username, password);
        this.confirm = confirm;
    }
    public boolean isConfirm() {

        return confirm;
    }
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;

    }
}