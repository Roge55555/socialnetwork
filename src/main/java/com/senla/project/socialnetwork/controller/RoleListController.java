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
@RequestMapping("/roleLists")
public class RoleListController {

    private final RoleListService roleListService;

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<RoleList> getAllRoleLists(@PathVariable("name") String name) {
        return roleListService.findAllWith(name);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public RoleList getById(@PathVariable("id") Long id) {
        return roleListService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public RoleList addRoleList(@RequestBody RoleList roleList) {
        return roleListService.add(roleList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateRoleList(@PathVariable("id") Long id, @RequestBody RoleList roleList) {
        roleListService.update(id, roleList);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteRoleList(@PathVariable("id") Long id) {
        roleListService.delete(id);
    }

}
