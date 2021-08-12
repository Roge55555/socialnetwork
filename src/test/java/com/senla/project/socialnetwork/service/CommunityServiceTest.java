package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor
class CommunityServiceTest {

    private final UserService userService;

    private final CommunityService communityService;

    @Test
    @DisplayName("Successful add community")
    void successAdd() {
        Community community = new Community();
        community.setCreator(userService.findById(2L));
        community.setName("Modern Guns");
        community.setDescription("Writing about history of modern pistols and rifles");
        community.setDateCreated(LocalDate.of(2021, 6, 21));

        final Community communit = communityService.add(community);
        Assertions.assertEquals(communityService.findById(3L), communit);
    }

    @Test
    @DisplayName("Exception when we trying to add community with not existing creator")
    void addTryingToUseNotExistingUser() {
        Community community = new Community();
        User user = userService.findById(2L);
        user.setId(5L);
        community.setCreator(user);
        community.setName("Modern Guns");
        community.setDescription("Writing about history of modern pistols and rifles");
        community.setDateCreated(LocalDate.of(2021, 6, 21));


        assertThatThrownBy(() -> communityService.add(community))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful showing all communities")
    void findAll() {
        final List<Community> communities = communityService.findAll();
        Assertions.assertEquals(2, communities.size());
    }

    @Test
    @DisplayName("Successful showing all communities in pages")
    void findAllWithPageable() {
        final Page<Community> communities = communityService.findAll(Pageable.ofSize(10));
        Assertions.assertEquals(2, communities.toList().size());
    }

    @Test
    @DisplayName("Successful finding community by id")
    void findByIdSuccess() {
        final List<Community> communities = communityService.findAll();
        Assertions.assertEquals(communities.get(1), communityService.findById(2L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing community by id")
    void findByIdException() {
        assertThatThrownBy(() -> communityService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating community by his id")
    void updateSuccess() {
        Community community = communityService.findById(1L);
        community.setCreator(userService.findById(2L));
        communityService.update(1L, community);
        Assertions.assertEquals(community, communityService.findById(1L));

    }

    @Test
    @DisplayName("Exception when we trying to update not existing community")
    void updateNoSuchCommunity() {
        Community community = communityService.findById(1L);
        User user = userService.findById(3L);
        community.setCreator(user);
        assertThatThrownBy(() -> communityService.update(12L, community))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update community creator to not existing")
    void updateTryingToUseNotExistingUser() {
        Community community = communityService.findById(1L);
        User user = userService.findById(3L);
        user.setId(8L);
        community.setCreator(user);
        assertThatThrownBy(() -> communityService.update(12L, community))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting community")
    void deleteSuccess() {
        communityService.delete(1L);
        assertThatThrownBy(() -> communityService.findById(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing community")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> communityService.delete(6L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
