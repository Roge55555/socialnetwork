package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.model.dto.AuthenticationRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationRestService {

    ResponseEntity<?> login(AuthenticationRequestDTO requestDTO);

}
