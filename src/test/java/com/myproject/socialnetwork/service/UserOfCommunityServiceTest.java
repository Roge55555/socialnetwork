package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.Community;
import com.myproject.socialnetwork.entity.User;
import com.myproject.socialnetwork.entity.UserOfCommunity;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.security.JwtTokenProvider;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserOfCommunityServiceTest {

    private final UserOfCommunityService userOfCommunityService;

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

    private UserOfCommunity getUserOfCommunity(Long communityId, Long userId) {
        UserOfCommunity userOfCommunity = new UserOfCommunity();
        userOfCommunity.setCommunity(Community.builder().id(communityId).build());
        userOfCommunity.setUser(User.builder().id(userId).build());
        userOfCommunity.setDateEntered(LocalDate.of(2021, 9, 13));
        return userOfCommunity;
    }

    @Test
    @DisplayName("Successful add subscription")
    void successAdd() {
        getAuthentication("CtrogE", "131313");
        final UserOfCommunity existedSubscriber = userOfCommunityService.add(getUserOfCommunity(2L, 1L));
        Assertions.assertEquals(userOfCommunityService.findByCommunityIdAndUserId(2L, 1L), existedSubscriber);
    }

    @Test
    @DisplayName("Exception when not creator trying to add user in community")
    void addTryingToUseNotCreatorAccountForAdd() {
        assertThatThrownBy(() -> userOfCommunityService.add(getUserOfCommunity(2L, 1L)))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful showing all subscriptions of current user")
    void findAllCommunitiesOfUser() {
        getAuthentication("Stego", "333kazanov11");
        final List<UserOfCommunity> userOfCommunities = userOfCommunityService.findAllCommunitiesOfUser();
        assertAll(() -> assertEquals(3L, userOfCommunities.get(0).getCommunity().getId()),
                () -> Assertions.assertEquals(Utils.getLogin(), userOfCommunities.get(0).getUser().getLogin()),
                () -> assertEquals(4L, userOfCommunities.get(1).getCommunity().getId()),
                () -> assertEquals(Utils.getLogin(), userOfCommunities.get(1).getUser().getLogin()),
                () -> assertEquals(2, userOfCommunities.size()));
    }

    @Test
    @DisplayName("Successful showing all users of current community")
    void findAllUsersOfCommunity() {
        getAuthentication("Roma666", "54862");
        final List<UserOfCommunity> userOfCommunities = userOfCommunityService.findAllUsersOfCommunity(4L);
        assertAll(() -> assertEquals(2L, userOfCommunities.get(0).getUser().getId()),
                () -> assertEquals(3L, userOfCommunities.get(1).getUser().getId()),
                () -> assertEquals(5L, userOfCommunities.get(2).getUser().getId()),
                () -> assertEquals(3, userOfCommunities.size()));
    }

    @Test
    @DisplayName("Exception when not member of community trying to see list subscribers")
    void findAllUsersOfCommunityException() {
        getAuthentication("Roma666", "54862");
        assertThatThrownBy(() -> userOfCommunityService.findAllUsersOfCommunity(1L))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful finding subscription by name of community and login of user")
    void findByCommunityNameAndUserLoginSuccess() {
        getAuthentication("$a$ha", "sava997");
        Assertions.assertEquals(userOfCommunityService.add(getUserOfCommunity(4L, 6L)), userOfCommunityService.findByCommunityNameAndUserLogin("FilmLover", "runsha").get());
    }

    @Test
    @DisplayName("Successful finding subscription by id of community and id of user")
    void findByCommunityIdAndUserIdSuccess() {
        getAuthentication("$a$ha", "sava997");
        Assertions.assertEquals(userOfCommunityService.add(getUserOfCommunity(4L, 6L)), userOfCommunityService.findByCommunityIdAndUserId(4L, 6L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing subscription by id of community and id of user")
    void findByCommunityIdAndUserIdExceptionNoElement() {
        getAuthentication("$a$ha", "sava997");
        assertThatThrownBy(() -> userOfCommunityService.findByCommunityIdAndUserId(4L, 1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception, only creator can trying to find subscription by id of community and id of user")
    void findByCommunityIdAndUserIdExceptionNotCreator() {
        getAuthentication("CtrogE", "131313");
        assertThatThrownBy(() -> userOfCommunityService.findByCommunityIdAndUserId(4L, 5L))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful exit from community by subscriber")
    void deleteSubscriberSuccess() {
        getAuthentication("CtrogE", "131313");
        userOfCommunityService.delete(1L, 2L);
        getAuthentication("rogE", "55555");
        assertThatThrownBy(() -> userOfCommunityService.findByCommunityIdAndUserId(1L, 2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting subscriber by admin")
    void adminDeleteSubscriberSuccess() {
        userOfCommunityService.delete(1L, 2L);
        assertThatThrownBy(() -> userOfCommunityService.findByCommunityIdAndUserId(1L, 2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when other subscriber of community trying to delete subscriber")
    void deleteNoSuchId() {
        getAuthentication("zagadka111", "f345t54tg433r");
        assertThatThrownBy(() -> userOfCommunityService.delete(3L, 4L))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }
}
