package com.josephadogeri.contact_management_app.dtos.request;

import com.josephadogeri.contact_management_app.abstracts.AbstractAuthCredentials;

public class UserDeactivateRequestDTO extends AbstractAuthCredentials {

    private boolean confirm;

    public UserDeactivateRequestDTO(String username, String password, boolean confirm) {

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