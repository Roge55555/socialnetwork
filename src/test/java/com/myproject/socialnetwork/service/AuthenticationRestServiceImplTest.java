package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.model.dto.AuthenticationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AuthenticationRestServiceImplTest {

    private final AuthenticationRestService authenticationRestService;

    @Test
    @DisplayName("Success login")
    void loginSuccess() {
        Map<Object, Object> response = new HashMap<>();
        assertEquals(ResponseEntity.ok(response).getStatusCode(), authenticationRestService.login(new AuthenticationRequestDTO("rogE", "55555")).getStatusCode());
    }

    @Test
    @DisplayName("Exception login")
    void loginException() {
        assertEquals(new ResponseEntity<>("Invalid login/password!", HttpStatus.FORBIDDEN),
                authenticationRestService.login(new AuthenticationRequestDTO("wrong", "data")));
    }
}