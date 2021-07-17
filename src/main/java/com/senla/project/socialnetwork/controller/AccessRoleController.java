package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.service.AccessRoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class AccessRoleController {

    private final AccessRoleService accessRoleService;

    @GetMapping("/accessRoles/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public AccessRole getById(@PathVariable("id") Long id) {
        return accessRoleService.findById(id);
    }
}
