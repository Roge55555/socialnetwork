package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.LoginEmailPhoneAlreadyTakenException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.model.ChangePassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserServiceTest {

    @Autowired
    private AccessRoleService accessRoleService;

    @Autowired
    private UserService userService;

    private User getUser() {
        User user = new User();
        user.setLogin("33333");
        user.setPassword("123456789");
        user.setDateBirth(LocalDate.of(1990, 10, 21));
        user.setFirstName("Greg");
        user.setLastName("Abramov");
        user.setEmail("greg@mail.ru");
        user.setPhone("+3358885522");
        user.setRole(accessRoleService.findById(1L));
        user.setIsActive(true);
        user.setIsBlocked(false);
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
        User user = getUser();

        final User existedUser = userService.add(user);
        Assertions.assertEquals(userService.findById(4L), existedUser);
    }

    @Test
    @DisplayName("Exception when we trying to add user with occupied login")
    void addTryingToRepeatLogin() {
        User userLogin = getUser();
        userLogin.setLogin("rogE");
        assertThatThrownBy(() -> userService.add(userLogin))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add user with occupied email")
    void addTryingToRepeatEmail() {
        User userEmail = getUser();
        userEmail.setEmail("sportzman@gmail.com");
        assertThatThrownBy(() -> userService.add(userEmail))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add user with occupied phone number")
    void addTryingToRepeatPhone() {
        User userPhone = getUser();
        userPhone.setPhone("+375333236700");
        assertThatThrownBy(() -> userService.add(userPhone))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    @DisplayName("Successful showing all users")
    void findAll() {
        final List<User> users = userService.findAll();
        Assertions.assertEquals(3, users.size());
    }

    @Test
    @DisplayName("Successful showing all users in pages")
    void findAllWithPageable() {
        final Page<User> users = userService.findAll(Pageable.ofSize(10));
        Assertions.assertEquals(3, users.toList().size());
    }

    @Test
    @DisplayName("Successful finding user by his id")
    void findByIdSuccess() {
        final List<User> users = userService.findAll();
        Assertions.assertEquals(users.get(0), userService.findById(1L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing user by id")
    void findByIdException() {
        assertThatThrownBy(() -> userService.findById(5L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding user by his login")
    void findByLogin() {
        final List<User> users = userService.findAll();
        Assertions.assertEquals(users.get(1), userService.findByLogin("CtrogE"));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing user by login")
    void findByLoginException() {
        assertThatThrownBy(() -> userService.findByLogin("Misha"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating user by his id")
    void updateSuccess() {
        User userLogin = userService.findById(2L);
        userLogin.setEmail("gfreg@mail.ru");
        userLogin.setPhone("+33331115522");
        userService.update(2L, userLogin);
        Assertions.assertEquals(userLogin, userService.findById(2L));

    }

    @Test
    @DisplayName("Exception when we trying to update not existing user")
    void updateNoSuchElement() {
        User userLogin = userService.findById(2L);
        userLogin.setEmail("gfreg@mail.ru");
        userLogin.setPhone("+33331115522");
        assertThatThrownBy(() -> userService.update(12L, userLogin))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update user login to occupied value")
    void updateTryingToRepeatUniqueLogin() {
        User userLogin = userService.findById(2L);
        userLogin.setLogin("rogE");
        assertThatThrownBy(() -> userService.update(2L, userLogin))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update user email to occupied value")
    void updateTryingToRepeatUniqueEmail() {
        User userEmail = userService.findById(1L);
        userEmail.setEmail("sportzman@gmail.com");
        assertThatThrownBy(() -> userService.update(1L, userEmail))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update user phone to occupied value")
    void updateTryingToRepeatUniquePhone() {
        User userPhone = userService.findById(1L);
        userPhone.setPhone("+375293486999");
        assertThatThrownBy(() -> userService.update(1L, userPhone))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    @DisplayName("Successful deleting user")
    void deleteSuccess() {
        userService.delete(3L);
        assertThatThrownBy(() -> userService.findById(3L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing user")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> userService.delete(13L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful changing user password")
    void changePasswordSuccess() {
        String newPassword = "22222";
        userService.changePassword(3L, new ChangePassword("54862", newPassword));
        Assertions.assertTrue(new BCryptPasswordEncoder(12).matches(newPassword, userService.findById(3L).getPassword()));
    }

    @Test
    @DisplayName("Exception when we trying to change password for not existing user")
    void changePasswordNoSuchUser() {

        assertThatThrownBy(() -> userService.changePassword(7L, new ChangePassword("54862", "rlfdlfd")))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we input not right password")
    void changePasswordNotRightOldPassword() {
        assertThatThrownBy(() -> userService.changePassword(3L, new ChangePassword("notRightPassword", "rlfdlfd")))
                .isInstanceOf(NotOldPasswordException.class);
    }
}
