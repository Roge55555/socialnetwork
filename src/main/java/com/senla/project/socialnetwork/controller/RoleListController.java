package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.service.RoleListService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class RoleListController {

    private final RoleListService roleListService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleListController.class);

    @GetMapping("/roleLists")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<RoleList> getAllRoleLists() {
        LOGGER.debug("Entering findAll roles endpoint");
        return roleListService.findAll();
    }

    @GetMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public RoleList getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById role endpoint");
        return roleListService.findById(id);
    }

    @PostMapping("/roleLists")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public RoleList addRoleList(@RequestBody RoleList roleList) {
        LOGGER.debug("Entering addRoleList endpoint");
        return roleListService.add(roleList);
    }

    @PutMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateRoleList(@PathVariable("id") Long id, @RequestBody RoleList roleList) {
        LOGGER.debug("Entering updateRoleList endpoint");
        roleListService.update(id, roleList);
    }

    @DeleteMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteRoleList(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteRoleList endpoint");
        roleListService.delete(id);
    }
}
