package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.entity.User;
import com.myproject.socialnetwork.model.dto.AuthenticationRequestDTO;
import com.myproject.socialnetwork.security.JwtTokenProvider;
import com.myproject.socialnetwork.service.UserService;
import com.myproject.socialnetwork.service.AuthenticationRestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationRestServiceImpl implements AuthenticationRestService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenProvider tokenProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public ResponseEntity<?> login(AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getLogin(), requestDTO.getPassword()));
            User user = userService.findByLogin(requestDTO.getLogin());
            String token = tokenProvider.createToken(user.getLogin(), user.getPassword());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", requestDTO.getLogin());
            response.put("token", token);
            LOGGER.info("Logged in success - {} .", requestDTO.getLogin());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            LOGGER.error("Invalid login/password!");
            return new ResponseEntity<>("Invalid login/password!", HttpStatus.FORBIDDEN);
        }
    }

}