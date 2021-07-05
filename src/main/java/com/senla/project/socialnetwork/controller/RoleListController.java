package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.service.RoleListService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class RoleListController {
    private final RoleListService roleListService;

    public RoleListController(RoleListService roleListService) {
        this.roleListService = roleListService;
    }

    @GetMapping("/roleLists")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<RoleList> getAllEmployees(){
        return roleListService.findAll();
    }

    @GetMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public RoleList getById(@PathVariable("id") Long id) {
        return roleListService.findById(id);
    }

    @PostMapping("/roleLists")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void addContact(@RequestBody RoleList roleList) {
        roleListService.add(roleList);
    }

    @PutMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody RoleList roleList) {
        roleListService.update(id, roleList);
    }

    @DeleteMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        roleListService.delete(id);
    }
}
