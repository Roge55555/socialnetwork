package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.Contact;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.myproject.socialnetwork.model.filter.ContactFilterRequest;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ContactServiceTest {

    private final UserService userService;

    private final ContactService contactService;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        getAuthentication("runsha", "64654564rererer");
    }

    private void getAuthentication(String login, String password) {
        String token = jwtTokenProvider.createToken(login, password);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Successful add contact")
    void successAdd() {
        final Contact contact = contactService.add(1L);
        assertEquals(contactService.findById(5L), contact);
    }

    @Test
    @DisplayName("Exception when we trying to contact to yourself")
    void addTryingToContactYourself() {
        assertThatThrownBy(() -> contactService.add(userService.findByLogin(Utils.getLogin()).getId()))
                .isInstanceOf(TryingRequestToYourselfException.class);
    }

    @Test
    @DisplayName("Successful showing all contacts with filter")
    void findAll() {
        ContactFilterRequest contactFilterRequest = new ContactFilterRequest();
        final List<Contact> contacts = contactService.findAll(contactFilterRequest);
        assertAll(() -> assertEquals(7L, contacts.get(0).getCreator().getId()),
                () -> assertEquals(6L, contacts.get(0).getMate().getId()),
                () -> assertEquals(5L, contacts.get(1).getCreator().getId()),
                () -> assertEquals(6L, contacts.get(1).getMate().getId()),
                () -> assertEquals(2, contacts.size()));
    }

    @Test
    @DisplayName("Successful finding contact by id")
    void findByIdSuccess() {
        final Contact contacts = contactService.findById(3L);
        assertAll(() -> assertEquals(7L, contacts.getCreator().getId()),
                () -> assertEquals(6L, contacts.getMate().getId()));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing contact by id")
    void findByIdException() {
        assertThatThrownBy(() -> contactService.findById(9L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when not mate of creator trying to take info about contact by id")
    void findByIdNotMateOrCreatorException() {
        getAuthentication("$a$ha", "sava997");
        assertThatThrownBy(() -> contactService.findById(3L)).isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful request to contact by his id")
    void acceptSuccess() {
        getAuthentication("rogE", "55555");
        contactService.acceptRequest(2L);
        assertTrue(contactService.findById(2L).getContactLevel());

    }

    @Test
    @DisplayName("Exception when user trying to accept not his request")
    void acceptOtherRequestException() {
        assertThatThrownBy(() -> contactService.acceptRequest(2L))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Successful updating role of mate in contact")
    void updateRoleSuccess() {
        contactService.updateRole(4L, 4L);
        assertEquals(4L, contactService.findById(4L).getCreatorRole().getId());

    }

    @Test
    @DisplayName("Successful deleting contact by creator -> full delete")
    void deleteContactSuccess() {
        getAuthentication("zagadka111", "f345t54tg433r");
        contactService.delete(3L);
        assertThatThrownBy(() -> contactService.findById(3L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting contact by mate -> make creator your subscriber")
    void downgradeContactSuccess() {
        contactService.delete(3L);
        final Contact contact = contactService.findById(3L);
        assertAll(() -> assertEquals(6L, contact.getMate().getId()),
                () -> assertEquals(7L, contact.getCreator().getId()),
                () -> assertFalse(contact.getContactLevel()));
    }
}
