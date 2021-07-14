package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AccessRoleServiceTest {

    @Autowired
    AccessRoleService accessRoleService;

    @Test
    void findByIdSuccess() {
        AccessRole role = new AccessRole();
        role.setId(2L);
        role.setName(Role.USER);
        Assertions.assertEquals(role, accessRoleService.findById(2L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> accessRoleService.findById(3L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findByRoleSuccess() {
        Assertions.assertEquals(accessRoleService.findById(1L), accessRoleService.findByName(Role.ADMIN));
    }

    @Test
    @Disabled
    void findByRoleException() {//TODO как такое может быть?!?!?!?!
//        assertThatThrownBy(() -> accessRoleService.findByName())
//                .isInstanceOf(NoSuchElementException.class);
    }
}