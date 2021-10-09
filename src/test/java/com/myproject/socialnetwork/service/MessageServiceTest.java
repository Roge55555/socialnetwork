package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.Message;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.model.filter.MessageFilterRequest;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MessageServiceTest {

    private final MessageService messageService;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        getAuthentication("zagadka111", "f345t54tg433r");
    }

    private void getAuthentication(String login, String password) {
        String token = jwtTokenProvider.createToken(login, password);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Successful add private message")
    void successAdd() {
        getAuthentication("$a$ha", "sava997");
        final Message message = messageService.add(5L, "hi");
        Assertions.assertEquals(messageService.findById(10L), message);
    }

    @Test
    @DisplayName("Successful showing all private messages with filter")
    void findAll() {
        MessageFilterRequest request = new MessageFilterRequest();
        request.setMateId(6L);
        final List<Message> messages = messageService.findAll(request);
        assertAll(() -> assertEquals(5L, messages.get(0).getId()),
                () -> assertEquals(8L, messages.get(1).getId()),
                () -> assertEquals(6L, messages.get(2).getId()),
                () -> assertEquals(9L, messages.get(3).getId()),
                () -> assertEquals(7L, messages.get(4).getId()),
                () -> assertEquals(5, messages.size()));
    }

    @Test
    @DisplayName("Successful finding private message by id")
    void findByIdSuccess() {
        getAuthentication("rogE", "55555");
        Assertions.assertEquals("shaking like hell on interview...", messageService.findById(4L).getMessageTxt());
    }

    @Test
    @DisplayName("Exception when we trying to find not existing private message by id")
    void findByIdNoElementException() {
        assertThatThrownBy(() -> messageService.findById(23L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to find private message from not your dialog by id")
    void findByIdNotYourDialogException() {
        assertThatThrownBy(() -> messageService.findById(2L))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful updating private message by id")
    void updateSuccess() {
        final Message message = messageService.update(6L, "TEST");
        Assertions.assertEquals(messageService.findById(6L), message);

    }

    @Test
    @DisplayName("Exception when we trying to update not your private message")
    void updateNoYourMessageException() {
        getAuthentication("runsha", "64654564rererer");
        assertThatThrownBy(() -> messageService.update(6L, "TEST"))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful deleting private message")
    void deleteSuccess() {
        messageService.delete(7L);
        assertThatThrownBy(() -> messageService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not your private message")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> messageService.delete(1L))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }
}
