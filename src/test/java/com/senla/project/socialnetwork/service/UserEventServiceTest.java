package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.entity.UserEvent;
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
class UserEventServiceTest {

    private final UserService userService;

    private final UserEventService userEventService;

    @Test
    @DisplayName("Successful add event")
    void successAdd() {
        UserEvent userEvent = new UserEvent();
        userEvent.setUser(userService.findById(2L));
        userEvent.setName("Birthday");
        userEvent.setDescription("Today birthday of " + userService.findById(2L).getFirstName() + "!");
        userEvent.setDate(LocalDate.of(2021, 8, 25));

        final UserEvent event = userEventService.add(userEvent);
        Assertions.assertEquals(userEventService.findById(2L), event);
    }

    @Test
    @DisplayName("Exception when we trying to add event with not existing user")
    void addTryingToUseNotExistingUser() {
        UserEvent userEvent = new UserEvent();
        User user = userService.findById(2L);
        user.setId(6L);
        userEvent.setUser(user);


        assertThatThrownBy(() -> userEventService.add(userEvent))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful showing all events")
    void findAll() {
        final List<UserEvent> userEvents = userEventService.findAll();
        Assertions.assertEquals(1, userEvents.size());
    }

    @Test
    @DisplayName("Successful finding event by id")
    void findByIdSuccess() {
        final List<UserEvent> userEvents = userEventService.findAll();
        Assertions.assertEquals(userEvents.get(0), userEventService.findById(1L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing event by id")
    void findByIdException() {
        assertThatThrownBy(() -> userEventService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating event by id")
    void updateSuccess() {
        UserEvent subscriber = userEventService.findById(1L);
        subscriber.setUser(userService.findById(2L));
        userEventService.update(1L, subscriber);
        Assertions.assertEquals(subscriber, userEventService.findById(1L));

    }

    @Test
    @DisplayName("Exception when we trying to update not existing event")
    void updateNoSuchElement() {
        UserEvent userEvent = userEventService.findById(1L);
        userEvent.setUser(userService.findById(3L));
        assertThatThrownBy(() -> userEventService.update(18L, userEvent))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update user of event to a not existing")
    void updateNoSuchUser() {
        UserEvent subscriber = userEventService.findById(1L);
        subscriber.setUser(userService.findById(1L));
        assertThatThrownBy(() -> userEventService.update(12L, subscriber))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting event")
    void deleteSuccess() {
        userEventService.delete(1L);
        assertThatThrownBy(() -> userEventService.findById(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing event")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> userEventService.delete(3L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
