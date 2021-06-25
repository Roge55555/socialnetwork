package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.service.RoleListService;
import org.springframework.http.HttpStatus;
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
    public List<RoleList> getAllEmployees(){
        return roleListService.findAll();
    }

    @GetMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public RoleList getById(@PathVariable("id") Long id) {
        return roleListService.findById(id);
    }

    @PostMapping("/roleLists")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody RoleList roleList) {
        roleListService.add(roleList);
    }

    @PutMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody RoleList roleList) {
        roleListService.update(id, roleList);
    }

    @DeleteMapping("/roleLists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        roleListService.delete(id);
    }
}
