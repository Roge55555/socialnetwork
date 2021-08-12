package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest

@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor
class RoleListServiceTest {

    private final RoleListService roleListService;

    @Test
    @DisplayName("Successful add role")
    void successAdd() {
        RoleList roleList = new RoleList();
        roleList.setName("Wife");

        final RoleList role = roleListService.add(roleList);
        Assertions.assertEquals(roleListService.findById(6L), role);
    }

    @Test
    @DisplayName("Successful showing all roles")
    void findAll() {
        final List<RoleList> roleLists = roleListService.findAll();
        Assertions.assertEquals(5, roleLists.size());
    }

    @Test
    @DisplayName("Successful finding role by his id")
    void findByIdSuccess() {
        final List<RoleList> roleLists = roleListService.findAll();
        Assertions.assertEquals(roleLists.get(4), roleListService.findById(5L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing role by id")
    void findByIdException() {
        assertThatThrownBy(() -> roleListService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating role by id")
    void updateSuccess() {
        RoleList roleList = roleListService.findById(3L);
        roleList.setName("Uncle");
        roleListService.update(3L, roleList);
        Assertions.assertEquals(roleList, roleListService.findById(3L));

    }

    @Test
    @DisplayName("Exception when we trying to update not existing role")
    void updateNoSuchElement() {
        RoleList roleList = roleListService.findById(3L);
        roleList.setName("Uncle");
        assertThatThrownBy(() -> roleListService.update(9L, roleList))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting role")
    void deleteSuccess() {
        roleListService.delete(4L);
        assertThatThrownBy(() -> roleListService.findById(4L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing role")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> roleListService.delete(15L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
