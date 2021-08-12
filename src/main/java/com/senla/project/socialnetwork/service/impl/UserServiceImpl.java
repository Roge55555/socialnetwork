package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.DataAlreadyTakenException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.model.ChangePassword;
import com.senla.project.socialnetwork.model.Role;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.AccessRoleService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AccessRoleService accessRoleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User add(User user) {
        LOGGER.info("Trying to add user.");

        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));

        user.setRole(accessRoleService.findByName(Role.USER));
        user.setRegistrationDate(LocalDate.now());

        if (userRepository.findByLogin(user.getLogin()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent() ||
                userRepository.findByPhone(user.getPhone()).isPresent()) {
            LOGGER.error("Login/Email/Phone already occupied");
            throw new DataAlreadyTakenException();
        }
        final User save = userRepository.save(user);
        LOGGER.info("User added.");
        return save;
    }

    @Override
    public List<User> findAll() {
        LOGGER.info("Trying to show all users.");
        if (userRepository.findAll().isEmpty()) {
            LOGGER.warn("User`s list is empty!");
        } else {
            LOGGER.info("User(s) found.");
        }
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        LOGGER.info("Trying to show all users in pages.");
        if (userRepository.findAll().isEmpty())
            LOGGER.warn("User`s list is empty!");
        else
            LOGGER.info("User(s) found.");
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        LOGGER.info("Trying to find user by id");
        final User user = userRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("User found using id {}", user.getId());
        return user;
    }

    @Override
    public User findByLogin(String login) {
        LOGGER.info("Trying to find user by login");
        final User user = userRepository.findByLogin(login).orElseThrow(() -> {
            LOGGER.error("No element with such login - {}.", login);
            return new NoSuchElementException("login - " + login + ".");
        });
        LOGGER.info("User with login {} found.", user.getLogin());
        return user;
    }


    @Override
    public User update(Long id, User user) {
        LOGGER.info("Trying to update user with id - {}.", id);
        //TODO: refactoring
//        if ((userRepository.findByLogin(user.getLogin()).isPresent() && userRepository.findById(id).isPresent() && !userRepository.findByLogin(user.getLogin()).get().getId().equals(id)) ||
//                (userRepository.findByEmail(user.getEmail()).isPresent() && userRepository.findById(id).isPresent() && !userRepository.findByEmail(user.getEmail()).get().getId().equals(id)) ||
//                (userRepository.findByPhone(user.getPhone()).isPresent() && userRepository.findById(id).isPresent() && !userRepository.findByPhone(user.getPhone()).get().getId().equals(id))) {
//            LOGGER.error("Login/Email/Phone already occupied" + id);
//            throw new DataAlreadyTakenException();
//        }
        final User save = userRepository.save(userRepository.findById(id).map(usr ->
                User.builder()
                        .id(id)
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
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                }));
        LOGGER.info("User with id {} updated.", id);
        return save;
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete user with id - {}.", id);
        if (userRepository.findById(id).isEmpty()) {
            LOGGER.error("No user with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        userRepository.deleteById(id);
        LOGGER.info("User with id - {} was deleted.", id);
    }

    @Override
    public void changePassword(Long id, ChangePassword password) {
        LOGGER.info("Trying to change password for User with id - {}.", id);
        if (userRepository.findById(id).isEmpty()) {
            LOGGER.error("No user with id - {}", id);
            throw new NoSuchElementException(id);
        } else if (new BCryptPasswordEncoder(12).matches(password.getOldPassword(), userRepository.findById(id).get().getPassword())) {
            userRepository.findById(id).map(usr -> {
                usr.setPassword(new BCryptPasswordEncoder(12).encode(password.getNewPassword()));
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
