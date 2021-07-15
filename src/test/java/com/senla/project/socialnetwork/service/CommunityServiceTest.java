package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CommunityServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    CommunityService communityService;

    @Test
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
    void findAll() {
        final List<Community> communities = communityService.findAll();
        Assertions.assertEquals(2, communities.size());
    }

    @Test
    void findByIdSuccess() {
        final List<Community> communities = communityService.findAll();
        Assertions.assertEquals(communities.get(1), communityService.findById(2L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> communityService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        Community community = communityService.findById(1L);
        community.setCreator(userService.findById(2L));
        communityService.update(1L, community);
        Assertions.assertEquals(community, communityService.findById(1L));

    }

    @Test
    void updateNoSuchCommunity() {
        Community community = communityService.findById(1L);
        User user = userService.findById(3L);
        community.setCreator(user);
        assertThatThrownBy(() -> communityService.update(12L, community))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingUser() {
        Community community = communityService.findById(1L);
        User user = userService.findById(3L);
        user.setId(8L);
        community.setCreator(user);
        assertThatThrownBy(() -> communityService.update(12L, community))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteSuccess() {
        communityService.delete(2L);
        assertThatThrownBy(() -> communityService.findById(2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> communityService.delete(6L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
