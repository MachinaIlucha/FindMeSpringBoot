package com.findme.findme.entity;

public enum RoleName {
    USER,
    ADMIN,
    SUPER_ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
