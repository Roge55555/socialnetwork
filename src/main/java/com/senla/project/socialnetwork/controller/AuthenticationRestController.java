package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.model.dto.AuthenticationRequestDTO;
import com.senla.project.socialnetwork.service.AuthenticationRestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final AuthenticationRestService authenticationRestService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        LOGGER.debug("Entering login endpoint");
        return authenticationRestService.login(requestDTO);
    }

}
