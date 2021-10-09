package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.ProfileComment;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.model.filter.ProfileCommentFilterRequest;
import com.myproject.socialnetwork.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ProfileCommentServiceTest {

    private final UserService userService;

    private final ProfileCommentService profileCommentService;

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

    private ProfileComment getProfileComment() {
        ProfileComment profileComment = new ProfileComment();
        profileComment.setProfileOwner(userService.findById(2L));
        profileComment.setCommentTxt("Test comment");
        return profileComment;
    }

    @Test
    @DisplayName("Successful add comment")
    void successAdd() {
        final ProfileComment profileComment = profileCommentService.add(getProfileComment());
        assertEquals(profileCommentService.findById(6L), profileComment);
    }

    @Test
    @DisplayName("Successful showing all comments with filter")
    void findAll() {
        ProfileCommentFilterRequest request = new ProfileCommentFilterRequest();
        request.setOwnerId(6L);
        final List<ProfileComment> profileComments = profileCommentService.findAll(request);
        assertAll(() -> assertEquals(3L, profileComments.get(0).getId()),
                () -> assertEquals(4L, profileComments.get(1).getId()),
                () -> assertEquals(2, profileComments.size()));
    }

    @Test
    @DisplayName("Successful finding comment by id")
    void findByIdSuccess() {
        assertEquals("first comment", profileCommentService.findById(1L).getCommentTxt());
    }

    @Test
    @DisplayName("Exception when we trying to find not existing comment by id")
    void findByIdException() {
        assertThatThrownBy(() -> profileCommentService.findById(11L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating comment by id")
    void updateSuccess() {
        getAuthentication("CtrogE", "131313");
        final ProfileComment profileComment = profileCommentService.update(1L, "TEST");
        assertEquals(profileComment, profileCommentService.findById(1L));

    }

    @Test
    @DisplayName("Exception when we trying to update not our comment")
    void updateNotOurCommentException() {
        assertThatThrownBy(() -> profileCommentService.update(1L, "TEST"))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful deleting comment by profile owner")
    void deleteByOwnerSuccess() {
        getAuthentication("runsha", "64654564rererer");
        profileCommentService.delete(4L);
        assertThatThrownBy(() -> profileCommentService.findById(4L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting comment by message creator")
    void deleteByCreatorSuccess() {
        profileCommentService.delete(4L);
        assertThatThrownBy(() -> profileCommentService.findById(4L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing comment")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> profileCommentService.delete(3L)).isInstanceOf(TryingModifyNotYourDataException.class);
    }
}
