package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.FOUND)
    public User getById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        userService.add(user);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        userService.update(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
