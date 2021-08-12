package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.model.ChangePassword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User add(User user);

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    User findById(Long id);

    User findByLogin(String login);

    User update(Long id, User user);

    void delete(Long id);

    void changePassword(Long id, ChangePassword password);
}
