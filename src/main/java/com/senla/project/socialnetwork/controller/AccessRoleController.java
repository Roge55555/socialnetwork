package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.service.AccessRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accessRoles")
public class AccessRoleController {

    private final AccessRoleService accessRoleService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public AccessRole getById(@PathVariable("id") Long id) {
        return accessRoleService.findById(id);
    }

}
