package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.entity.User;
import com.myproject.socialnetwork.model.Role;
import com.myproject.socialnetwork.repository.UserRepository;
import com.myproject.socialnetwork.service.AccessRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SimpleListener {

    private final UserRepository userRepository;

    private final AccessRoleService accessRoleService;

    private final PasswordEncoder passwordEncoder;

    @JmsListener(destination = "simple.queue")
    public void receiveOrder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(accessRoleService.findByName(Role.USER));
        user.setRegistrationDate(LocalDate.now());

        userRepository.save(user);
    }

}
