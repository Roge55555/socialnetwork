package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor
class UserOfCommunityServiceTest {

    private final UserService userService;

    private final CommunityService communityService;

    private final UserOfCommunityService userOfCommunityService;


    @Test
    @DisplayName("Successful add subscription")
    void successAdd() {
        UserOfCommunity userOfCommunity = new UserOfCommunity();
        userOfCommunity.setCommunity(communityService.findById(2L));
        userOfCommunity.setUser(userService.findById(1L));
        userOfCommunity.setDateEntered(LocalDate.of(2021, 7, 13));

        final UserOfCommunity existedSubscriber = userOfCommunityService.add(userOfCommunity);
        Assertions.assertEquals(userOfCommunityService.findById(3L), existedSubscriber);
    }

    @Test
    @DisplayName("Exception when we trying to subscription with not existing user")
    void addTryingToUseNotExistingUser() {
        UserOfCommunity subscriberUser = userOfCommunityService.findById(2L);
        User user = userService.findById(3L);
        user.setId(5L);
        subscriberUser.setUser(user);
        assertThatThrownBy(() -> userOfCommunityService.add(subscriberUser))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add subscription with not existing community")
    void addTryingToUseNotExistingCommunity() {
        UserOfCommunity subscriberCommunity = userOfCommunityService.findById(2L);
        Community community = communityService.findById(1L);
        community.setId(4L);
        subscriberCommunity.setCommunity(community);
        assertThatThrownBy(() -> userOfCommunityService.add(subscriberCommunity))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful showing all subscriptions")
    void findAll() {
        final List<UserOfCommunity> userOfCommunities = userOfCommunityService.findAll();
        Assertions.assertEquals(2, userOfCommunities.size());
    }

    @Test
    @DisplayName("Successful finding subscription by id")
    void findByIdSuccess() {
        final List<UserOfCommunity> userOfCommunities = userOfCommunityService.findAll();
        Assertions.assertEquals(userOfCommunities.get(0), userOfCommunityService.findById(1L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing subscription by id")
    void findByIdException() {
        assertThatThrownBy(() -> userOfCommunityService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting subscription")
    void deleteSuccess() {
        userOfCommunityService.delete(2L);
        assertThatThrownBy(() -> userOfCommunityService.findById(2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing subscription")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> userOfCommunityService.delete(3L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
