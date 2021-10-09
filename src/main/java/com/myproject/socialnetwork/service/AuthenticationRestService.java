package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.model.dto.AuthenticationRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationRestService {

    ResponseEntity<?> login(AuthenticationRequestDTO requestDTO);

}
