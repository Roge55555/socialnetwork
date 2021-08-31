package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.model.Role;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.AccessRoleService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AccessRoleService accessRoleService;

    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User add(User user) {
        LOGGER.info("Trying to add user.");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(accessRoleService.findByName(Role.USER));
        user.setRegistrationDate(LocalDate.now());

        final User save = userRepository.save(user);
        LOGGER.info("User added.");
        return save;
    }

    @Override
    public List<User> findAll() {
        LOGGER.info("Trying to show all users.");
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(String name, Pageable pageable) {
        LOGGER.info("Trying to show all users in pages.");
        return userRepository.findByLoginContainingOrFirstNameContainingOrLastNameContaining(name, name, name, pageable);
    }

    @Override
    public User findById(Long id) {
        LOGGER.info("Trying to find user by id");
        final User user = userRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("User found using id {}", user.getId());
        return user;
    }

    @Override
    public User findByLogin(String login) {
        LOGGER.info("Trying to find user by login");
        final User user = userRepository.findByLogin(login).orElseThrow(() -> {
            LOGGER.error("No element with such login - {}.", login);
            throw new NoSuchElementException("login - " + login + ".");
        });
        LOGGER.info("User with login {} found.", user.getLogin());
        return user;
    }

    @Override
    public User update(User user) {
        LOGGER.info("Trying to update user with id - {}.", findByLogin(Utils.getLogin()).getId());

        return userRepository.save(userRepository.findById(findByLogin(Utils.getLogin()).getId()).map(usr ->
                User.builder()
                        .id(findByLogin(Utils.getLogin()).getId())
                        .login(user.getLogin())
                        .password(usr.getPassword())
                        .dateBirth(user.getDateBirth())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .role(usr.getRole())
                        .registrationDate(usr.getRegistrationDate())
                        .phone(user.getPhone())
                        .website(user.getWebsite())
                        .aboutYourself(user.getAboutYourself())
                        .jobTitle(user.getJobTitle())
                        .workPhone(user.getWorkPhone())
                        .build())
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", findByLogin(Utils.getLogin()).getId());
                    throw new NoSuchElementException(findByLogin(Utils.getLogin()).getId());
                }));
    }

    @Override
    public void delete() {
        LOGGER.info("Trying to delete user - {}.", Utils.getLogin());
        userRepository.deleteById(findByLogin(Utils.getLogin()).getId());
        LOGGER.info("User - {} was deleted.", Utils.getLogin());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        LOGGER.info("Trying to change password for User with id - {}.", findByLogin(Utils.getLogin()).getId());
        if (passwordEncoder.matches(oldPassword, findById(findByLogin(Utils.getLogin()).getId()).getPassword())) {
            userRepository.findById(findByLogin(Utils.getLogin()).getId()).map(usr -> {
                usr.setPassword(passwordEncoder.encode(newPassword));
                final User save = userRepository.save(usr);
                LOGGER.info("Password was changed.");
                return save;
            }).orElseThrow();
        } else {
            LOGGER.error("Not right old user password");
            throw new NotOldPasswordException();
        }
    }

}
