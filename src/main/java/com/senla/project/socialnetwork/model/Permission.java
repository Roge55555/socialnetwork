package com.senla.project.socialnetwork.model;

public enum Permission {

    USERS_PERMISSION("standard:permission"),
    ADMINS_PERMISSION("communities:permission");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
