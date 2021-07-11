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

//    @GetMapping("/accessRoles")
//    public List<AccessRole> getAllEmployees(){
//        return accessRoleService.findAll();
//    }

    @GetMapping("/accessRoles/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public AccessRole getById(@PathVariable("id") Long id) {
        return accessRoleService.findById(id);
    }

//    @PostMapping("/accessRoles")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addContact(@RequestBody AccessRole accessRole) {
//        accessRoleService.add(accessRole);
//    }
//
//    @PutMapping("/accessRoles/{id}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void updateContact(@PathVariable("id") Long id, @RequestBody AccessRole accessRole) {
//        accessRoleService.update(id, accessRole);
//    }
//
//    @DeleteMapping("/accessRoles/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteContact(@PathVariable("id") Long id) {
//        accessRoleService.delete(id);
//    }
}