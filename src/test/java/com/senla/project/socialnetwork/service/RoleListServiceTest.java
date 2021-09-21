package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RoleListServiceTest {

    private final RoleListService roleListService;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        String token = jwtTokenProvider.createToken("rogE", "55555");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Successful add role")
    void successAdd() {
        final RoleList role = roleListService.add("TEST");
        assertEquals(roleListService.findById(6L), role);
    }

    @Test
    @DisplayName("Successful showing all roles with substring")
    void findAll() {
        final List<RoleList> roleLists = roleListService.findAllWith("r");
        assertAll(() -> assertEquals("Friend", roleLists.get(0).getName()),
                () -> assertEquals("Best Friend", roleLists.get(1).getName()),
                () -> assertEquals("Workmate", roleLists.get(2).getName()),
                () -> assertEquals("Director", roleLists.get(3).getName()),
                () -> assertEquals(4, roleLists.size()));
    }

    @Test
    @DisplayName("Successful finding role by id")
    void findByIdSuccess() {
        assertEquals("Me ^_^", roleListService.findById(5L).getName());
    }

    @Test
    @DisplayName("Exception when we trying to find not existing role by id")
    void findByIdException() {
        assertThatThrownBy(() -> roleListService.findById(7L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting role")
    void deleteSuccess() {
        roleListService.delete(4L);
        assertThatThrownBy(() -> roleListService.findById(4L)).isInstanceOf(NoSuchElementException.class);
    }

}
