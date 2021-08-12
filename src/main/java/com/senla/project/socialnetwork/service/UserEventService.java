package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserEvent;

import java.util.List;

public interface UserEventService {

    UserEvent add(UserEvent userEvent);

    List<UserEvent> findAll();

    UserEvent findById(Long id);

    UserEvent update(Long id, UserEvent userEvent);

    void delete(Long id);

}
