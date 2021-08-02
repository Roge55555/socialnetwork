package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.model.AuthenticationRequestDTO;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.AuthenticationRestService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final AuthenticationRestService authenticationRestService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        LOGGER.debug("Entering login endpoint");
        return authenticationRestService.login(requestDTO);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('standard:permission')")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationRestService.logout(request);

//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response, null);


    }
}
