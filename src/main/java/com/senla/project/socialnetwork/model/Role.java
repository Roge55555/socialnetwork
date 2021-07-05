package com.senla.project.socialnetwork.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USERS_PERMISSION)),  //запихнуть данные из репы ролей?!
    ADMIN(Set.of(Permission.USERS_PERMISSION, Permission.ADMINS_PERMISSION));
    //мб 3я роль - админ конкретного коммунити

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
