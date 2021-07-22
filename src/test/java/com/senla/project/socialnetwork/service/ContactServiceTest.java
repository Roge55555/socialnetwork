package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ContactServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    RoleListService roleListService;

    @Test
    @DisplayName("Successful add contact")
    void successAdd() {
        Contact contact = new Contact();
        contact.setCreator(userService.findById(2L));
        contact.setMate(userService.findById(3L));
        contact.setDateConnected(LocalDate.of(2021, 7, 5));
        contact.setContactLevel(false);
        contact.setContactRole(roleListService.findById(1L));

        final Contact contactN3 = contactService.add(contact);
        Assertions.assertEquals(contactService.findById(3L), contactN3);
    }

    @Test
    @DisplayName("Exception when we trying to add contact with not existing creator")
    void addTryingToUseNotExistingCreator() {
        Contact contactCreator = contactService.findById(2L);
        User user = userService.findById(3L);
        user.setId(5L);
        contactCreator.setCreator(user);
        assertThatThrownBy(() -> contactService.add(contactCreator))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add contact with not existing mate")
    void addTryingToUseNotExistingMate() {
        Contact contactMate = contactService.findById(2L);
        User user = userService.findById(3L);
        user.setId(9L);
        contactMate.setMate(user);
        assertThatThrownBy(() -> contactService.add(contactMate))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to add contact with not existing role")
    void addTryingToUseNotExistingRole() {
        Contact contactRole = contactService.findById(2L);
        RoleList roleList = roleListService.findById(5L);
        roleList.setId(7L);
        contactRole.setContactRole(roleList);
        assertThatThrownBy(() -> contactService.add(contactRole))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to contact to yourself")
    void addTryingToContactYourself() {
        Contact contactYourself = contactService.findById(2L);
        contactYourself.setCreator(userService.findById(2L));
        contactYourself.setMate(userService.findById(2L));
        assertThatThrownBy(() -> contactService.add(contactYourself))
                .isInstanceOf(TryingRequestToYourselfException.class);
    }

    @Test
    @DisplayName("Successful showing all contacts")
    void findAll() {
        final List<Contact> contacts = contactService.findAll();
        Assertions.assertEquals(2, contacts.size());
    }

    @Test
    @DisplayName("Successful finding contact by id")
    void findByIdSuccess() {
        final List<Contact> contacts = contactService.findAll();
        Assertions.assertEquals(contacts.get(1), contactService.findById(2L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing contact by id")
    void findByIdException() {
        assertThatThrownBy(() -> contactService.findById(4L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful updating contact by his id")
    void updateSuccess() {
        Contact contact = contactService.findById(2L);
        contact.setCreator(userService.findById(1L));
        contact.setMate(userService.findById(3L));
        contact.setContactRole(roleListService.findById(3L));
        contactService.update(2L, contact);
        Assertions.assertEquals(contact, contactService.findById(2L));

    }

    @Test
    @DisplayName("Exception when we trying to update not existing contact")
    void updateNoSuchElement() {
        Contact contact = contactService.findById(2L);
        contact.setCreator(userService.findById(1L));
        contact.setMate(userService.findById(3L));
        contact.setContactRole(roleListService.findById(3L));
        assertThatThrownBy(() -> contactService.update(3L, contact))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update creator in contact to not existing")
    void updateTryingToUseNotExistingCreator() {
        Contact contactCreator = contactService.findById(2L);
        User user = userService.findById(3L);
        user.setId(5L);
        contactCreator.setCreator(user);
        assertThatThrownBy(() -> contactService.update(2L, contactCreator))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update mate in contact to not existing")
    void updateTryingToUseNotExistingMate() {
        Contact contactMate = contactService.findById(2L);
        User user = userService.findById(3L);
        user.setId(9L);
        contactMate.setMate(user);
        assertThatThrownBy(() -> contactService.update(2L, contactMate))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update role in contact to not existing")
    void updateTryingToUseNotExistingRole() {
        Contact contactRole = contactService.findById(2L);
        RoleList roleList = roleListService.findById(5L);
        roleList.setId(7L);
        contactRole.setContactRole(roleList);
        assertThatThrownBy(() -> contactService.update(2L, contactRole))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to update contact to make it to yourself")
    void updateTryingToContactYourself() {
        Contact contactYourself = contactService.findById(2L);
        contactYourself.setCreator(userService.findById(2L));
        contactYourself.setMate(userService.findById(2L));
        assertThatThrownBy(() -> contactService.update(2L, contactYourself))
                .isInstanceOf(TryingRequestToYourselfException.class);
    }

    @Test
    @DisplayName("Successful deleting contact")
    void deleteSuccess() {
        contactService.delete(2L);
        assertThatThrownBy(() -> contactService.findById(2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete not existing contact")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> contactService.delete(3L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
