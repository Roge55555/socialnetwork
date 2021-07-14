package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserOfCommunityServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    CommunityService communityService;

    @Autowired
    UserOfCommunityService userOfCommunityService;


    @Test
    void successAdd() {
        UserOfCommunity userOfCommunity = new UserOfCommunity();
        userOfCommunity.setCommunity(communityService.findById(2L));
        userOfCommunity.setUser(userService.findById(1L));
        userOfCommunity.setDateEntered(LocalDate.of(2021, 7, 13));

        final UserOfCommunity existedSubscriber = userOfCommunityService.add(userOfCommunity);
        Assertions.assertEquals(userOfCommunityService.findById(3L), existedSubscriber);
    }

    @Test
    void addTryingToUseNotExistingValues() {
        UserOfCommunity subscriberUser = userOfCommunityService.findById(2L);
        User user = userService.findById(3L);
        user.setId(5L);
        subscriberUser.setUser(user);
        assertThatThrownBy(() -> userOfCommunityService.add(subscriberUser))
                .isInstanceOf(NoSuchElementException.class);

        UserOfCommunity subscriberCommunity = userOfCommunityService.findById(2L);
        Community community = communityService.findById(1L);
        community.setId(4L);
        subscriberCommunity.setCommunity(community);
        assertThatThrownBy(() -> userOfCommunityService.add(subscriberCommunity))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findAll() {
        final List<UserOfCommunity> userOfCommunities = userOfCommunityService.findAll();
        Assertions.assertEquals(2, userOfCommunities.size());
    }

    @Test
    void findByIdSuccess() {
        final List<UserOfCommunity> userOfCommunities = userOfCommunityService.findAll();
        Assertions.assertEquals(userOfCommunities.get(0), userOfCommunityService.findById(1L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> userOfCommunityService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        UserOfCommunity subscriber = userOfCommunityService.findById(2L);
        subscriber.setUser(userService.findById(3L));
        subscriber.setCommunity(communityService.findById(2L));
        userOfCommunityService.update(2L, subscriber);
        Assertions.assertEquals(subscriber, userOfCommunityService.findById(2L));

    }

    @Test
    void updateNoSuchElement() {
        UserOfCommunity subscriber = userOfCommunityService.findById(2L);
        subscriber.setUser(userService.findById(3L));
        subscriber.setCommunity(communityService.findById(2L));
        assertThatThrownBy(() -> userOfCommunityService.update(12L, subscriber))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingValues() {
        UserOfCommunity subscriberUser = userOfCommunityService.findById(1L);
        User user = userService.findById(3L);
        user.setId(5L);
        subscriberUser.setUser(user);
        assertThatThrownBy(() -> userOfCommunityService.update(1L, subscriberUser))
                .isInstanceOf(NoSuchElementException.class);

        UserOfCommunity subscriberCommunity = userOfCommunityService.findById(1L);
        Community community = communityService.findById(2L);
        community.setId(4L);
        subscriberCommunity.setCommunity(community);
        assertThatThrownBy(() -> userOfCommunityService.update(1L, subscriberCommunity))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteSuccess() {
        userOfCommunityService.delete(2L);
        assertThatThrownBy(() -> userOfCommunityService.findById(2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> userOfCommunityService.delete(3L))
                .isInstanceOf(NoSuchElementException.class);
    }

}