package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.CommunityMessage;
import com.myproject.socialnetwork.exeptions.NoAccessForBlockedUserException;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.model.filter.CommunityMessageFilterRequest;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CommunityMessageServiceTest {

    private final UserService userService;

    private final CommunityService communityService;

    private final CommunityMessageService communityMessageService;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        getAuthentication("Stego", "333kazanov11");
    }

    private void getAuthentication(String login, String password) {
        String token = jwtTokenProvider.createToken(login, password);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private CommunityMessage getCommunityMessage(Long communityId) {
        CommunityMessage communityMessage = new CommunityMessage();
        communityMessage.setCommunity(communityService.findById(communityId));
        communityMessage.setCreator(userService.findByLogin(Utils.getLogin()));
        communityMessage.setDate(LocalDateTime.of(2021, 8, 14, 18, 36, 58));
        communityMessage.setTxt("Text in community for testing");
        return communityMessage;
    }

    @Test
    @DisplayName("Successful add community message")
    void successAdd() {
        getAuthentication("CtrogE", "131313");
        final CommunityMessage message = communityMessageService.add(getCommunityMessage(2L));
        Assertions.assertEquals(communityMessageService.findById(13L), message);
    }

    @Test
    @DisplayName("Exception when not community member trying to write message")
    void addTryingToUseNotExistingUser() {
        assertThatThrownBy(() -> communityMessageService.add(getCommunityMessage(1L)))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Exception when blocked member trying to write message")
    void addByBlockedUser() {
        getAuthentication("rogE", "55555");
        assertThatThrownBy(() -> communityMessageService.add(getCommunityMessage(3L)))
                .isInstanceOf(NoAccessForBlockedUserException.class);
    }

    @Test
    @DisplayName("Successful showing all community messages with selected filter")
    void findAll() {
        CommunityMessageFilterRequest request = new CommunityMessageFilterRequest();
        request.setCommunityId(3L);
        final List<CommunityMessage> communityMessages = communityMessageService.findAll(request);
        assertAll(() -> assertEquals(8L, communityMessages.get(0).getId()),
                () -> assertEquals(9L, communityMessages.get(1).getId()),
                () -> assertEquals(10L, communityMessages.get(2).getId()),
                () -> assertEquals(11L, communityMessages.get(3).getId()),
                () -> assertEquals(12L, communityMessages.get(4).getId()),
                () -> assertEquals(5, communityMessages.size()));
    }

    @Test
    @DisplayName("Exception when blocked member trying to see community messages")
    void findAllByBlockedUserException() {
        getAuthentication("Roma666", "54862");
        CommunityMessageFilterRequest request = new CommunityMessageFilterRequest();
        request.setCommunityId(3L);
        assertThatThrownBy(() -> communityMessageService.findAll(request)).isInstanceOf(NoAccessForBlockedUserException.class);
    }

    @Test
    @DisplayName("Exception when not member of community trying to see community messages")
    void findAllByNotMemberException() {
        CommunityMessageFilterRequest request = new CommunityMessageFilterRequest();
        request.setCommunityId(1L);
        assertThatThrownBy(() -> communityMessageService.findAll(request)).isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful finding community message by id")
    void findByIdSuccess() {
        CommunityMessageFilterRequest request = new CommunityMessageFilterRequest();
        request.setCommunityId(4L);
        final List<CommunityMessage> communityMessages = communityMessageService.findAll(request);
        assertAll(() -> Assertions.assertEquals(communityMessages.get(0), communityMessageService.findById(4L)),
                () -> Assertions.assertEquals(communityMessages.get(1), communityMessageService.findById(5L)),
                () -> Assertions.assertEquals(communityMessages.get(2), communityMessageService.findById(6L)),
                () -> Assertions.assertEquals(communityMessages.get(3), communityMessageService.findById(7L)),
                () -> assertEquals(4, communityMessages.size()));
    }

    @Test
    @DisplayName("Exception when blocked member trying to find community message by id")
    void findByIdByBlockedUserException() {
        getAuthentication("Roma666", "54862");
        assertThatThrownBy(() -> communityMessageService.findById(8L)).isInstanceOf(NoAccessForBlockedUserException.class);
    }

    @Test
    @DisplayName("Exception when not member of community trying to find community message by id")
    void findByIdByNotMemberException() {
        getAuthentication("CtrogE", "131313");
        assertThatThrownBy(() -> communityMessageService.findById(8L)).isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Exception when we trying to find not existing community message by id")
    void findByIdException() {
        assertThatThrownBy(() -> communityMessageService.findById(26L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating community message by his id")
    void updateSuccess() {
        final CommunityMessage communityMessage = communityMessageService.update(10L, "TESTTESTTEST");
        Assertions.assertEquals(communityMessage, communityMessageService.findById(10L));

    }

    @Test
    @DisplayName("Exception when not message creator trying to update community message")
    void updateByNotMessageCreator() {
        getAuthentication("runsha", "64654564rererer");
        assertThatThrownBy(() -> communityMessageService.update(10L, "TESTTESTTEST"))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful deleting community message")
    void deleteSuccess() {
        communityMessageService.delete(10L);
        assertThatThrownBy(() -> communityMessageService.findById(10L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when not creator of community/creator of message trying to delete community message")
    void deleteNotMemberException() {
        getAuthentication("$a$ha", "sava997");
        assertThatThrownBy(() -> communityMessageService.delete(10L)).isInstanceOf(TryingModifyNotYourDataException.class);
    }

}
