package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProfileCommentServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ProfileCommentService profileCommentService;

    @Test
    void successAdd() {
        ProfileComment profileComment = new ProfileComment();
        profileComment.setProfileOwner(userService.findById(2L));
        profileComment.setUser(userService.findById(3L));
        profileComment.setDate(LocalDateTime.of(2021, 7, 1, 13, 50, 21));
        profileComment.setCommentTxt("Test comment");

        final ProfileComment comment = profileCommentService.add(profileComment);
        Assertions.assertEquals(profileCommentService.findById(3L), profileComment);
    }

    @Test
    void addTryingToUseNotExistingUsers() {
        ProfileComment commentUser = profileCommentService.findById(2L);
        User user = userService.findById(3L);
        user.setId(5L);
        commentUser.setUser(user);
        assertThatThrownBy(() -> profileCommentService.add(commentUser))
                .isInstanceOf(NoSuchElementException.class);

        ProfileComment commentOwner = profileCommentService.findById(1L);
        user = userService.findById(3L);
        user.setId(4L);
        commentOwner.setProfileOwner(user);
        assertThatThrownBy(() -> profileCommentService.add(commentOwner))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findAll() {
        final List<ProfileComment> profileComments = profileCommentService.findAll();
        Assertions.assertEquals(2, profileComments.size());
    }

    @Test
    void findByIdSuccess() {
        final List<ProfileComment> profileComments = profileCommentService.findAll();
        Assertions.assertEquals(profileComments.get(1), profileCommentService.findById(2L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> profileCommentService.findById(4L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        ProfileComment comment = profileCommentService.findById(1L);
        comment.setUser(userService.findById(3L));
        comment.setUser(userService.findById(2L));
        profileCommentService.update(1L, comment);
        Assertions.assertEquals(comment, profileCommentService.findById(1L));

    }

    @Test
    void updateNoSuchElement() {
        ProfileComment comment = profileCommentService.findById(2L);
        comment.setUser(userService.findById(3L));
        comment.setUser(userService.findById(2L));
        assertThatThrownBy(() -> profileCommentService.update(6L, comment))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingUsers() {
        ProfileComment commentUser = profileCommentService.findById(2L);
        User user = userService.findById(3L);
        user.setId(5L);
        commentUser.setUser(user);
        assertThatThrownBy(() -> profileCommentService.update(1L, commentUser))
                .isInstanceOf(NoSuchElementException.class);

        ProfileComment commentOwner = profileCommentService.findById(1L);
        user = userService.findById(3L);
        user.setId(4L);
        commentOwner.setProfileOwner(user);
        assertThatThrownBy(() -> profileCommentService.update(2L, commentOwner))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteSuccess() {
        profileCommentService.delete(2L);
        assertThatThrownBy(() -> profileCommentService.findById(2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> profileCommentService.delete(3L))
                .isInstanceOf(NoSuchElementException.class);
    }
}