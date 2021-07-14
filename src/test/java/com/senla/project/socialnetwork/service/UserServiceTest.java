package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.LoginEmailPhoneAlreadyTakenException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.model.ChangePassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@ExtendWith(MockitoExtension.class)//????????????
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
    void successAdd() {
        User user = getUser();

        final User existedUser = userService.add(user);
        Assertions.assertEquals(userService.findById(4L), existedUser);
    }

    @Test
    void addTryingToRepeatUniqueValues() {
        User userLogin = getUser();
        userLogin.setLogin("rogE");
        assertThatThrownBy(() -> userService.add(userLogin))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);

        User userEmail = getUser();
        userEmail.setEmail("sportzman@gmail.com");
        assertThatThrownBy(() -> userService.add(userEmail))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);

        User userPhone = getUser();
        userPhone.setPhone("+375333236700");
        assertThatThrownBy(() -> userService.add(userPhone))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    void findAll() {
        final List<User> users = userService.findAll();
        Assertions.assertEquals(3, users.size());
    }

    @Test
    void findByIdSuccess() {
        final List<User> users = userService.findAll();
        Assertions.assertEquals(users.get(0), userService.findById(1L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> userService.findById(5L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findByLogin() {
        final List<User> users = userService.findAll();
        Assertions.assertEquals(users.get(1), userService.findByLogin("CtrogE"));
    }

    @Test
    void updateSuccess() {
        User userLogin = userService.findById(2L);
        userLogin.setEmail("gfreg@mail.ru");
        userLogin.setPhone("+33331115522");
        userService.update(2L, userLogin);
        Assertions.assertEquals(userLogin, userService.findById(2L));

    }

    @Test
    void updateNoSuchElement() {
        User userLogin = userService.findById(2L);
        userLogin.setEmail("gfreg@mail.ru");
        userLogin.setPhone("+33331115522");
        assertThatThrownBy(() -> userService.update(12L, userLogin))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToRepeatUniqueLogin() {
        User userLogin = userService.findById(2L);
        userLogin.setLogin("rogE");
        assertThatThrownBy(() -> userService.update(2L, userLogin))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    void updateTryingToRepeatUniqueEmail() {
        User userEmail = userService.findById(1L);
        userEmail.setEmail("sportzman@gmail.com");
        assertThatThrownBy(() -> userService.update(1L, userEmail))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    void updateTryingToRepeatUniquePhone() {
        User userPhone = userService.findById(1L);
        userPhone.setPhone("+375293486999");
        assertThatThrownBy(() -> userService.update(1L, userPhone))
                .isInstanceOf(LoginEmailPhoneAlreadyTakenException.class);
    }

    @Test
    @Disabled
    void deleteAccess() {
        userService.delete(3L);
        assertThatThrownBy(() -> userService.findById(3L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> userService.delete(13L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void changePasswordAccess() {
        String newPassword = "22222";
        userService.changePassword(3L, new ChangePassword("54862", newPassword));
        Assertions.assertTrue(new BCryptPasswordEncoder(12).matches(newPassword, userService.findById(3L).getPassword()));
    }

    @Test
    void changePasswordNoSuchUser() {

        assertThatThrownBy(() -> userService.changePassword(7L, new ChangePassword("54862", "rlfdlfd")))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void changePasswordNotRightOldPassword() {
        assertThatThrownBy(() -> userService.changePassword(3L, new ChangePassword("notRightPassword", "rlfdlfd")))
                .isInstanceOf(NotOldPasswordException.class);
    }

}