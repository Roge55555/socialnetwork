package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
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
class CommunityServiceTest {

    private final UserService userService;

    private final CommunityService communityService;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        getAuthentication("rogE", "55555");
    }

    private void getAuthentication(String login, String password) {
        String token = jwtTokenProvider.createToken(login, password);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Community getCommunity(String name) {
        Community community = new Community();
        community.setName(name);
        community.setDescription("Writing about history of modern pistols and rifles");
        return community;
    }

    @Test
    @DisplayName("Successful add community")
    void successAdd() {
        getAuthentication("zagadka111", "f345t54tg433r");
        final Community check = communityService.add(getCommunity("Test"));
        final Community community = communityService.add(getCommunity("Modern Guns"));
        assertEquals(communityService.findById(check.getId() + 1), community);
    }

    @Test
    @DisplayName("Successful showing all communities")
    void findAll() {
        getAuthentication("CtrogE", "131313");
        final Community community = communityService.add(getCommunity("Modern Guns"));
        final List<Community> communities = communityService.findAll();
        assertAll(() -> assertEquals(2L, communities.get(0).getId()),
                () -> assertEquals(community, communities.get(1)),
                () -> assertEquals(2, communities.size()));
    }

    @Test
    @DisplayName("Successful finding community by id")
    void findByIdSuccess() {
        final List<Community> communities = communityService.findAll();
        assertEquals(communities.get(0), communityService.findById(1L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing community by id")
    void findByIdException() {
        assertThatThrownBy(() -> communityService.findById(11L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding community by name")
    void findByNameSuccess() {
        final List<Community> communities = communityService.findAll();
        assertEquals(communities.get(0), communityService.findByName("admin`s home"));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing community by name")
    void findByNameException() {
        assertThatThrownBy(() -> communityService.findByName("admin`s TEST"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding communities by part of name")
    void searchByPartOfNameSuccess() {
        final List<Community> communities = communityService.searchBySubstringOfName("e");
        assertAll(() -> assertEquals("admin`s home", communities.get(0).getName()),
                () -> assertEquals("free community", communities.get(1).getName()),
                () -> assertEquals("FilmLover", communities.get(2).getName()),
                () -> assertEquals(3, communities.size()));
    }

    @Test
    @DisplayName("Successful updating community by his id")
    void updateSuccess() {
        final Community community = communityService.findByName("admin`s home");
        community.setCreator(userService.findById(2L));
        communityService.update(1L, community);
        Assertions.assertEquals(community, communityService.findById(1L));

    }

    @Test
    @DisplayName("Exception when we trying to make creator of community user with not enough permissions")
    void updateAccessNewAdminException() {
        Community community = communityService.findById(1L);
        User user = userService.findById(3L);
        community.setCreator(user);
        assertThatThrownBy(() -> communityService.update(7L, community))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting community")
    void deleteSuccess() {
        communityService.delete(1L);
        assertThatThrownBy(() -> communityService.findById(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

}
