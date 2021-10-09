package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.User;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.NotOldPasswordException;
import com.myproject.socialnetwork.model.Role;
import com.myproject.socialnetwork.repository.UserRepository;
import com.myproject.socialnetwork.service.UserService;
import com.myproject.socialnetwork.service.AccessRoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AccessRoleService accessRoleService;

    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(accessRoleService.findByName(Role.USER));
        user.setRegistrationDate(LocalDate.now());

        return userRepository.save(user);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public Page<User> findAll(String name, Pageable pageable) {
        return userRepository.findByLoginContainingOrFirstNameContainingOrLastNameContaining(name, name, name, pageable);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> {
            LOGGER.error("No element with such login - {}.", login);
            throw new NoSuchElementException("login - " + login + ".");
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public User update(User user) {
        User updatedUser = findByLogin(Utils.getLogin());
        if (Objects.nonNull(user.getDateBirth())) {
            updatedUser.setDateBirth(user.getDateBirth());
        }
        if (Objects.nonNull(user.getFirstName())) {
            updatedUser.setFirstName(user.getFirstName());
        }
        if (Objects.nonNull(user.getLastName())) {
            updatedUser.setLastName(user.getLastName());
        }
        if (Objects.nonNull(user.getEmail())) {
            updatedUser.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getPhone())) {
            updatedUser.setPhone(user.getPhone());
        }
        if (Objects.nonNull(user.getWebsite())) {
            updatedUser.setWebsite(user.getWebsite());
        }
        if (Objects.nonNull(user.getAboutYourself())) {
            updatedUser.setAboutYourself(user.getAboutYourself());
        }
        if (Objects.nonNull(user.getJobTitle())) {
            updatedUser.setJobTitle(user.getJobTitle());
        }
        if (Objects.nonNull(user.getWorkPhone())) {
            updatedUser.setWorkPhone(user.getWorkPhone());
        }

        return userRepository.save(updatedUser);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public void delete() {
        userRepository.deleteById(findByLogin(Utils.getLogin()).getId());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, NotOldPasswordException.class})
    @Override
    public void changePassword(final String oldPassword, final String newPassword) {
        if (passwordEncoder.matches(oldPassword, findByLogin(Utils.getLogin()).getPassword())) {
            User user = findByLogin(Utils.getLogin());
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            LOGGER.error("Not right old user password");
            throw new NotOldPasswordException();
        }
    }

}
