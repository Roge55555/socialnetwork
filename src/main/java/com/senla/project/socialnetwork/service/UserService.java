package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User add(User user);

    Page<User> findAll(String name, Pageable pageable);

    User findById(Long id);

    User findByLogin(String login);

    User update(User user);

    void delete();

    void changePassword(String oldPassword, String newPassword);

}
