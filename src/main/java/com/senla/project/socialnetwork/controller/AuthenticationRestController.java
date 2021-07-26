package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.model.AuthenticationRequestDTO;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    JwtTokenProvider tokenProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        LOGGER.debug("Entering login endpoint");
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
            User user_active = userRepository.findByLogin(requestDTO.getLogin()).get();
            user_active.setIsActive(true);
            userRepository.save(user_active);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            LOGGER.error("Invalid login/password!");
            return new ResponseEntity<>("Invalid login/password!", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('standard:permission')")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenProvider.resolveToken(request);


        User user_active = userRepository.findByLogin(tokenProvider.getLogin(token)).get();
        user_active.setIsActive(false);
        userRepository.save(user_active);

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);


    }
}
