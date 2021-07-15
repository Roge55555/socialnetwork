package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CommunityMessageServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    CommunityService communityService;

    @Autowired
    CommunityMessageService communityMessageService;


    @Test
    void successAdd() {
        CommunityMessage communityMessage = new CommunityMessage();
        communityMessage.setCommunity(communityService.findById(2L));
        communityMessage.setCreator(userService.findById(3L));
        communityMessage.setDate(LocalDateTime.of(2021, 7, 14, 18, 36, 58));
        communityMessage.setTxt("Text in community for testing");


        final CommunityMessage message = communityMessageService.add(communityMessage);
        Assertions.assertEquals(communityMessageService.findById(4L), message);
    }

    @Test
    void addTryingToUseNotExistingUser() {
        CommunityMessage messageUser = communityMessageService.findById(2L);
        User user = userService.findById(3L);
        user.setId(5L);
        messageUser.setCreator(user);
        assertThatThrownBy(() -> communityMessageService.add(messageUser))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void addTryingToUseNotExistingCommunity() {
        CommunityMessage messageCommunity = communityMessageService.findById(2L);
        Community community = communityService.findById(1L);
        community.setId(4L);
        messageCommunity.setCommunity(community);
        assertThatThrownBy(() -> communityMessageService.add(messageCommunity))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findAll() {
        final List<CommunityMessage> communityMessages = communityMessageService.findAll();
        Assertions.assertEquals(3, communityMessages.size());
    }

    @Test
    void findByIdSuccess() {
        final List<CommunityMessage> communityMessages = communityMessageService.findAll();
        Assertions.assertEquals(communityMessages.get(2), communityMessageService.findById(3L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> communityMessageService.findById(13L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        CommunityMessage communityMessage = communityMessageService.findById(2L);
        communityMessage.setCreator(userService.findById(3L));
        communityMessage.setCommunity(communityService.findById(2L));
        communityMessageService.update(2L, communityMessage);
        Assertions.assertEquals(communityMessage, communityMessageService.findById(2L));

    }

    @Test
    void updateNoSuchElement() {
        CommunityMessage communityMessage = communityMessageService.findById(2L);
        communityMessage.setCreator(userService.findById(3L));
        communityMessage.setCommunity(communityService.findById(2L));
        assertThatThrownBy(() -> communityMessageService.update(18L, communityMessage))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingUser() {
        CommunityMessage messageUser = communityMessageService.findById(3L);
        User user = userService.findById(3L);
        user.setId(5L);
        messageUser.setCreator(user);
        assertThatThrownBy(() -> communityMessageService.update(3L, messageUser))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingCommunity() {
        CommunityMessage messageCommunity = communityMessageService.findById(3L);
        Community community = communityService.findById(1L);
        community.setId(4L);
        messageCommunity.setCommunity(community);
        assertThatThrownBy(() -> communityMessageService.update(3L, messageCommunity))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteSuccess() {
        communityMessageService.delete(3L);
        assertThatThrownBy(() -> communityMessageService.findById(3L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> communityMessageService.delete(11L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
