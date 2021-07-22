package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.controller.dto.UserAddDTO;
import com.senla.project.socialnetwork.controller.dto.UserUpdateDTO;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.model.ChangePassword;
import com.senla.project.socialnetwork.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<User> getAllUsers() {
        LOGGER.debug("Entering findAll users endpoint");
        return userService.findAll();
    }

    @GetMapping("/usersPage")
    @PreAuthorize("hasAuthority('standard:permission')")
    public Page<User> getAllUsers(Pageable pageable) {
        LOGGER.debug("Entering findAll users in pages endpoint");
        return userService.findAll(pageable);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.FOUND)
    public User getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById user endpoint");
        return userService.findById(id);
    }

    @GetMapping("/users/find/{login}")
    @PreAuthorize("hasAuthority('standard:permission')")
    @ResponseStatus(HttpStatus.FOUND)
    public User getByLogin(@PathVariable("login") String login) {
        LOGGER.debug("Entering getByLogin user endpoint");
        return userService.findByLogin(login);
    }

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
        LOGGER.debug("Entering addUser endpoint");
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
        LOGGER.debug("Entering updateUser endpoint");
        userService.update(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteUser(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteUser endpoint");
        userService.delete(id);
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void changeUserPassword(@PathVariable("id") Long id, @Valid @RequestBody ChangePassword password) throws NoSuchElementException, NotOldPasswordException {
        LOGGER.debug("Entering changeUserPassword endpoint");
        userService.changePassword(id, password);
    }
}
