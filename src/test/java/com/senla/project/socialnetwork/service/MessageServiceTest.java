package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor
class MessageServiceTest {

    private final UserService userService;

    private final MessageService messageService;

    @Test
    @DisplayName("Successful add private message")
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
    @DisplayName("Exception when we trying to add private message with not existing sender")
    void addTryingToUseNotExistingSender() {
        Message messageSender = messageService.findById(4L);
        User user = userService.findById(1L);
        user.setId(6L);
        messageSender.setSender(user);
        assertThatThrownBy(() -> messageService.add(messageSender))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add private message with not existing receiver")
    void addTryingToUseNotExistingReceiver() {
        Message messageReceiver = messageService.findById(5L);
        User user = userService.findById(3L);
        user.setId(8L);
        messageReceiver.setReceiver(user);
        assertThatThrownBy(() -> messageService.add(messageReceiver))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful showing all private messages")
    void findAll() {
        final List<Message> messages = messageService.findAll();
        Assertions.assertEquals(5, messages.size());
    }

    @Test
    @DisplayName("Successful finding private message by id")
    void findByIdSuccess() {
        final List<Message> messages = messageService.findAll();
        Assertions.assertEquals(messages.get(3), messageService.findById(4L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing private message by id")
    void findByIdException() {
        assertThatThrownBy(() -> messageService.findById(6L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating private message by id")
    void updateSuccess() {
        Message message = messageService.findById(2L);
        message.setSender(userService.findById(3L));
        message.setReceiver(userService.findById(2L));
        messageService.update(2L, message);
        Assertions.assertEquals(message, messageService.findById(2L));

    }

    @Test
    @DisplayName("Exception when we trying to update not existing private message")
    void updateNoSuchElement() {
        Message message = messageService.findById(2L);
        message.setReceiver(userService.findById(3L));
        message.setSender(userService.findById(2L));
        assertThatThrownBy(() -> messageService.update(6L, message))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update sender of private message to a not existing")
    void updateTryingToUseNotExistingSender() {
        Message messageSender = messageService.findById(4L);
        User user = userService.findById(1L);
        user.setId(6L);
        messageSender.setSender(user);
        assertThatThrownBy(() -> messageService.update(4L, messageSender))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update receiver of private message to a not existing")
    void updateTryingToUseNotExistingReceiver() {
        Message messageReceiver = messageService.findById(5L);
        User user = userService.findById(3L);
        user.setId(8L);
        messageReceiver.setReceiver(user);
        assertThatThrownBy(() -> messageService.update(5L, messageReceiver))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting private message")
    void deleteSuccess() {
        messageService.delete(5L);
        assertThatThrownBy(() -> messageService.findById(5L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing private message")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> messageService.delete(11L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
