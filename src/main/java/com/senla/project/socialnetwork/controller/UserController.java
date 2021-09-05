package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.model.ChangePassword;
import com.senla.project.socialnetwork.model.dto.UserAddDTO;
import com.senla.project.socialnetwork.model.dto.UserUpdateDTO;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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

    @GetMapping("/search/{name}")
    @PreAuthorize("hasAuthority('standard:permission')")
    public Page<User> getAllUsers(@PathVariable("name") String name, Pageable pageable) {
        return userService.findAll(name, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.FOUND)
    public User getById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/find/{login}")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.FOUND)
    public User getByLogin(@PathVariable("login") String login) {
        return userService.findByLogin(login);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
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
        userService.update(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteUser() {
        userService.delete();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void changeUserPassword(@Valid @RequestBody ChangePassword password) {
        userService.changePassword(password.getOldPassword(), password.getNewPassword());
    }

}
