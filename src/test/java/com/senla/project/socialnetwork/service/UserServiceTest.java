package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceTest {

    private final AccessRoleService accessRoleService;


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        String token = jwtTokenProvider.createToken("rogE", "55555");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private User getUser() {
        User user = new User();
        user.setLogin("33333");
        user.setPassword(passwordEncoder.encode("123456789"));
        user.setDateBirth(LocalDate.of(1990, 10, 21));
        user.setFirstName("Greg");
        user.setLastName("Abramov");
        user.setEmail("greg@mail.ru");
        user.setPhone("+3358885522");
        user.setRole(accessRoleService.findById(1L));
        user.setRegistrationDate(LocalDate.now());
        user.setWebsite("www.refopl.org");
        user.setAboutYourself("about yourself3");
        user.setJobTitle("job title3");
        user.setWorkPhone("587436");
        return user;
    }

    @Test
    @DisplayName("Successful add user")
    void successAdd() {
        final User existedUser = userService.add(getUser());
        assertEquals(userService.findById(8L), existedUser);
    }

    @Test
    @DisplayName("Exception when we trying to add user with occupied login")
    void addTryingToRepeatLogin() {
        User userLogin = getUser();
        userLogin.setLogin("rogE");
        assertThatThrownBy(() -> userService.add(userLogin))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add user with occupied email")
    void addTryingToRepeatEmail() {
        User userEmail = getUser();
        userEmail.setEmail("sportzman@gmail.com");
        assertThatThrownBy(() -> userService.add(userEmail))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add user with occupied phone number")
    void addTryingToRepeatPhone() {
        User userPhone = getUser();
        userPhone.setPhone("+375333236700");
        assertThatThrownBy(() -> userService.add(userPhone))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Successful showing all users which login, name, surname include search string")
    void findAll() {

        final Page<User> users = userService.findAll("o", null);
        assertEquals(5L, users.getTotalElements());
    }

    @Test
    @DisplayName("Successful finding user by his id")
    void findByIdSuccess() {
        final Page<User> users = userService.findAll("", null);
        Iterator<User> iterator = users.iterator();
        iterator.next();
        iterator.next();
        assertEquals(iterator.next(), userService.findById(3L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing user by id")
    void findByIdException() {
        assertThatThrownBy(() -> userService.findById(12L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding user by his login")
    void findByLogin() {
        final Page<User> users = userService.findAll("", null);
        Iterator<User> iterator = users.iterator();
        iterator.next();
        assertEquals(iterator.next(), userService.findByLogin("CtrogE"));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing user by login")
    void findByLoginException() {
        assertThatThrownBy(() -> userService.findByLogin("Misha"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating with all parameters")
    void updateSuccessAll() {
        User user = userService.findById(1L);

        user.setDateBirth(LocalDate.of(1990, 10, 21));
        user.setFirstName("Greg");
        user.setLastName("Abramov");
        user.setEmail("greg@mail.ru");
        user.setPhone("+3358885522");
        user.setWebsite("www.refopl.org");
        user.setAboutYourself("about yourself3");
        user.setJobTitle("job title3");
        user.setWorkPhone("587436");

        final User updatedUser = userService.update(user);
        assertEquals(userService.findById(1L), updatedUser);

    }

    @Test
    @DisplayName("Successful updating user by his id")
    void updateSuccessWithNothing() {
        User user = new User();
        assertEquals(userService.update(user), userService.findById(1L));

    }

    @Test
    @DisplayName("Successful deleting user")
    void deleteSuccess() {
        userService.delete();
        assertThatThrownBy(() -> userService.findById(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful changing user password")
    void changePasswordSuccess() {
        String newPassword = "22222";
        userService.changePassword("55555", newPassword);
        assertTrue(new BCryptPasswordEncoder(12).matches(newPassword, userService.findById(1L).getPassword()));
    }

    @Test
    @DisplayName("Exception when we input not right password")
    void changePasswordNotRightOldPassword() {
        assertThatThrownBy(() -> userService.changePassword("notRightPassword", "rlfdlfd"))
                .isInstanceOf(NotOldPasswordException.class);
    }
}
