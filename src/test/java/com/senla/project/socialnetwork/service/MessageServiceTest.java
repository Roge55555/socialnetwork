package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MessageServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Test
    void successAdd() {
        Message message = new Message();
        message.setSender(userService.findById(2L));
        message.setReceiver(userService.findById(3L));
        message.setDateCreated(LocalDateTime.of(2021, 6, 28, 8, 17, 32));
        message.setMessageTxt("Test message");

        final Message messageN3 = messageService.add(message);
        Assertions.assertEquals(messageService.findById(6L), messageN3);
    }

    @Test
    void addTryingToUseNotExistingUsers() {
        Message messageSender = messageService.findById(4L);
        User user = userService.findById(1L);
        user.setId(6L);
        messageSender.setSender(user);
        assertThatThrownBy(() -> messageService.add(messageSender))
                .isInstanceOf(NoSuchElementException.class);

        Message messageReceiver = messageService.findById(5L);
        user = userService.findById(3L);
        user.setId(8L);
        messageReceiver.setReceiver(user);
        assertThatThrownBy(() -> messageService.add(messageReceiver))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findAll() {
        final List<Message> messages = messageService.findAll();
        Assertions.assertEquals(5, messages.size());
    }

    @Test
    void findByIdSuccess() {
        final List<Message> messages = messageService.findAll();
        Assertions.assertEquals(messages.get(3), messageService.findById(4L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> messageService.findById(6L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        Message message = messageService.findById(2L);
        message.setSender(userService.findById(3L));
        message.setReceiver(userService.findById(2L));
        messageService.update(2L, message);
        Assertions.assertEquals(message, messageService.findById(2L));

    }

    @Test
    void updateNoSuchElement() {
        Message message = messageService.findById(2L);
        message.setReceiver(userService.findById(3L));
        message.setSender(userService.findById(2L));
        assertThatThrownBy(() -> messageService.update(6L, message))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingUsers() {
        Message messageSender = messageService.findById(4L);
        User user = userService.findById(1L);
        user.setId(6L);
        messageSender.setSender(user);
        assertThatThrownBy(() -> messageService.update(4L, messageSender))
                .isInstanceOf(NoSuchElementException.class);

        Message messageReceiver = messageService.findById(5L);
        user = userService.findById(3L);
        user.setId(8L);
        messageReceiver.setReceiver(user);
        assertThatThrownBy(() -> messageService.update(5L, messageReceiver))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteSuccess() {
        messageService.delete(5L);
        assertThatThrownBy(() -> messageService.findById(5L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> messageService.delete(11L))
                .isInstanceOf(NoSuchElementException.class);
    }
}