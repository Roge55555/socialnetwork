package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.service.AccessRoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class AccessRoleController {

    private final AccessRoleService accessRoleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessRoleController.class);

    @GetMapping("/accessRoles/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public AccessRole getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById access role endpoint");
        return accessRoleService.findById(id);
    }
}
