package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.entity.UserEvent;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserEventServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserEventService userEventService;

    @Test
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
    void addTryingToUseNotExistingUser() {
        UserEvent userEvent = new UserEvent();
        User user = userService.findById(2L);
        user.setId(6L);
        userEvent.setUser(user);


        assertThatThrownBy(() -> userEventService.add(userEvent))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findAll() {
        final List<UserEvent> userEvents = userEventService.findAll();
        Assertions.assertEquals(1, userEvents.size());
    }

    @Test
    void findByIdSuccess() {
        final List<UserEvent> userEvents = userEventService.findAll();
        Assertions.assertEquals(userEvents.get(0), userEventService.findById(1L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> userEventService.findById(7L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        UserEvent subscriber = userEventService.findById(1L);
        subscriber.setUser(userService.findById(2L));
        userEventService.update(1L, subscriber);
        Assertions.assertEquals(subscriber, userEventService.findById(1L));

    }

    @Test
    void updateNoSuchUser() {
        UserEvent subscriber = userEventService.findById(1L);
        subscriber.setUser(userService.findById(1L));
        assertThatThrownBy(() -> userEventService.update(12L, subscriber))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteSuccess() {
        userEventService.delete(1L);
        assertThatThrownBy(() -> userEventService.findById(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> userEventService.delete(3L))
                .isInstanceOf(NoSuchElementException.class);
    }
}