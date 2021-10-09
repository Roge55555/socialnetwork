package com.myproject.socialnetwork.controller;

import com.myproject.socialnetwork.entity.RoleList;
import com.myproject.socialnetwork.service.RoleListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public RoleList addRoleList(@RequestBody String roleListName) {
        return roleListService.add(roleListName);
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<RoleList> getAllRoleListsWithName(@PathVariable("name") String name) {
        return roleListService.findAllWith(name);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public RoleList getById(@PathVariable("id") Long id) {
        return roleListService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteRoleList(@PathVariable("id") Long id) {
        roleListService.delete(id);
    }

}
