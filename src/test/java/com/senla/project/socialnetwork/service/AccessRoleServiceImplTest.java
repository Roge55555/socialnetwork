package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.model.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccessRoleServiceImplTest {

    private final AccessRoleService accessRoleService;

    @Test
    @DisplayName("Successful finding access role by his id")
    void findByIdSuccess() {
        AccessRole role = new AccessRole();
        role.setId(2L);
        role.setName(Role.USER);
        assertEquals(role, accessRoleService.findById(2L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing access role by id")
    void findByIdException() {
        assertThatThrownBy(() -> accessRoleService.findById(3L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding access role by his name")
    void findByRoleNameSuccess() {
        assertEquals(accessRoleService.findById(1L), accessRoleService.findByName(Role.ADMIN));
    }

}
