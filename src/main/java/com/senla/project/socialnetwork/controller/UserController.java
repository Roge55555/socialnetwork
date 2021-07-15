package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.controller.dto.UserUpdateDTO;
import com.senla.project.socialnetwork.controller.dto.UserAddDTO;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.model.ChangePassword;
import com.senla.project.socialnetwork.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.FOUND)
    public User getById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/users/find/{login}")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.FOUND)
    public User getByLogin(@PathVariable("login") String login) {
        return userService.findByLogin(login);
    }

//    @GetMapping("/user")
//    @PreAuthorize("hasAuthority('standard:permission')")
//    @ResponseStatus(HttpStatus.FOUND)
//    public User getByLogin(@RequestParam("login") String login) {
//        return userService.findByLogin(login);
//    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody UserAddDTO userAddDTO) {
        User user = User.builder()
                .login(userAddDTO.getLogin())
                .password(userAddDTO.getPassword())
                .dateBirth(userAddDTO.getDateBirth())
                .firstName(userAddDTO.getFirstName())
                .lastName(userAddDTO.getLastName())
                .email(userAddDTO.getEmail())
                .phone(userAddDTO.getPhone())
                .website(userAddDTO.getWebsite())
                .aboutYourself(userAddDTO.getAboutYourself())
                .jobTitle(userAddDTO.getJobTitle())
                .workPhone(userAddDTO.getWorkPhone())
                .build();
        return userService.add(user);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) throws NoSuchElementException {
        User user = User.builder()
                .login(userUpdateDTO.getLogin())
                .dateBirth(userUpdateDTO.getDateBirth())
                .firstName(userUpdateDTO.getFirstName())
                .lastName(userUpdateDTO.getLastName())
                .email(userUpdateDTO.getEmail())
                .phone(userUpdateDTO.getPhone())
                .website(userUpdateDTO.getWebsite())
                .aboutYourself(userUpdateDTO.getAboutYourself())
                .jobTitle(userUpdateDTO.getJobTitle())
                .workPhone(userUpdateDTO.getWorkPhone())
                .build();
        userService.update(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void changeUserPassword(@PathVariable("id") Long id, @Valid @RequestBody ChangePassword password) throws NoSuchElementException, NotOldPasswordException {
        userService.changePassword(id, password);
    }
}
