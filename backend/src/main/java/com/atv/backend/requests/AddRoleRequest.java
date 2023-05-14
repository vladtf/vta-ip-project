package com.atv.backend.requests;

import com.atv.backend.dao.entities.Role;

public class AddRoleRequest {

    private final String email;
    private final Role.Action action;


    public String getEmail() {
        return email;
    }

    public Role.Action getAction() {
        return action;
    }

    public AddRoleRequest(String email, Role.Action action) {
        this.email = email;
        this.action = action;
    }


}
