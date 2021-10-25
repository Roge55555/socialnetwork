package com.myproject.socialnetwork.controller;

import com.myproject.socialnetwork.entity.User;
import com.myproject.socialnetwork.model.dto.UserAddDTO;
import com.myproject.socialnetwork.service.JmsUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/JMS")
public class JMSUserController {

    private final JmsUserService jmsUserService;

    @PostMapping("/registration")
    public void addUser(@Valid @RequestBody UserAddDTO userAddDTO) {
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

        jmsUserService.addOrder(user);
    }

}
