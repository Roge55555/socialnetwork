package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.model.AuthenticationRequestDTO;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.security.JwtTokenProvider;
import com.senla.project.socialnetwork.service.AuthenticationRestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationRestServiceImpl implements AuthenticationRestService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtTokenProvider tokenProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestServiceImpl.class);

    @Override
    public ResponseEntity<?> login(AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getLogin(), requestDTO.getPassword()));
            User user = userRepository.findByLogin(requestDTO.getLogin()).orElseThrow(() -> {
                LOGGER.error("No account with such login - " + requestDTO.getLogin());
                return new UsernameNotFoundException("No such user");
            });
            String token = tokenProvider.createToken(user.getLogin(), user.getPassword());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", requestDTO.getLogin());
            response.put("token", token);
            LOGGER.info("Logged in success - " + requestDTO.getLogin() + ".");
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            LOGGER.error("Invalid login/password!");
            return new ResponseEntity<>("Invalid login/password!", HttpStatus.FORBIDDEN);
        }

    }
}
