package com.myproject.socialnetwork.controller;

import com.myproject.socialnetwork.model.dto.AuthenticationRequestDTO;
import com.myproject.socialnetwork.service.AuthenticationRestService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        return authenticationRestService.login(requestDTO);
    }

}
