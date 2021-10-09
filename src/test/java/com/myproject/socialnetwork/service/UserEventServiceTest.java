package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.UserEvent;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserEventServiceTest {

    private final UserEventService userEventService;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        getAuthentication("rogE", "55555");
    }

    private UserEvent getUserEvent(String name) {
        UserEvent userEvent = new UserEvent();
        userEvent.setName(name);
        userEvent.setDescription("Last day for fix tests!");
        userEvent.setDate(LocalDateTime.of(2021, 11, 25, 18, 0));
        return userEvent;
    }

    private void getAuthentication(String login, String password) {
        String token = jwtTokenProvider.createToken(login, password);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Successful add event")
    void successAdd() {
        final UserEvent check = userEventService.add(getUserEvent("check last id"));
        final UserEvent event = userEventService.add(getUserEvent("Deadline"));
        assertEquals(userEventService.findById(check.getId() + 1L), event);
    }


    @Test
    @DisplayName("Successful showing all events with substring")
    void findAll() {
        final String name = "birth";
        final List<UserEvent> userEvents = userEventService.findAllWith(name);
        assertAll(() -> Assertions.assertEquals(Utils.getLogin(), userEvents.get(0).getUser().getLogin()),
                () -> assertTrue(userEvents.get(0).getName().contains(name)),
                () -> assertEquals(Utils.getLogin(), userEvents.get(1).getUser().getLogin()),
                () -> assertTrue(userEvents.get(1).getName().contains(name)),
                () -> assertEquals(2, userEvents.size()));
    }

    @Test
    @DisplayName("Successful finding event by id")
    void findByIdSuccess() {
        getAuthentication("runsha", "64654564rererer");
        final List<UserEvent> userEvents = userEventService.findAllWith("dentist");
        assertAll(() -> assertEquals(userEvents.get(0), userEventService.findById(5L)),
                () -> assertEquals(1, userEvents.size()));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing event by id")
    void findByIdException() {
        assertThatThrownBy(() -> userEventService.findById(13L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating event with all parameters")
    void updateSuccess() {
        getAuthentication("Roma666", "54862");
        UserEvent userEvent = userEventService.findById(1L);
        userEvent.setName("TESTTEST");
        userEvent.setDescription("TEST DESCRIPTION");
        userEvent.setDate(LocalDateTime.of(2022, 11, 22, 11, 22, 11));
        UserEvent updatedUserEvent = userEventService.update(1L, userEvent);
        assertEquals(userEventService.findById(1L), updatedUserEvent);

    }

    @Test
    @DisplayName("Successful updating event by id")
    void updateSuccessWithNothing() {
        UserEvent userEvent = new UserEvent();
        assertEquals(userEventService.findById(3L), userEventService.update(3L, userEvent));
    }

    @Test
    @DisplayName("Successful deleting event")
    void deleteSuccess() {
        getAuthentication("Roma666", "54862");
        userEventService.delete(1L);
        assertThatThrownBy(() -> userEventService.findById(1L)).isInstanceOf(NoSuchElementException.class);
    }

}
