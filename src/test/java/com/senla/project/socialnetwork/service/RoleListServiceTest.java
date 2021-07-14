package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RoleListServiceTest {

    @Autowired
    RoleListService roleListService;

    @Test
    void successAdd() {
        RoleList roleList = new RoleList();
        roleList.setName("Wife");

        final RoleList role = roleListService.add(roleList);
        Assertions.assertEquals(roleListService.findById(6L), role);
    }

    @Test
    void findAll() {
        final List<RoleList> roleLists = roleListService.findAll();
        Assertions.assertEquals(5, roleLists.size());
    }

    @Test
    void findByIdSuccess() {
        final List<RoleList> roleLists = roleListService.findAll();
        Assertions.assertEquals(roleLists.get(4), roleListService.findById(5L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> roleListService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        RoleList roleList = roleListService.findById(3L);
        roleList.setName("Uncle");
        roleListService.update(3L, roleList);
        Assertions.assertEquals(roleList, roleListService.findById(3L));

    }

    @Test
    void updateNoSuchElement() {
        RoleList roleList = roleListService.findById(3L);
        roleList.setName("Uncle");
        assertThatThrownBy(() -> roleListService.update(9L, roleList))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteSuccess() {
        roleListService.delete(4L);
        assertThatThrownBy(() -> roleListService.findById(4L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> roleListService.delete(15L))
                .isInstanceOf(NoSuchElementException.class);
    }
}