package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.UserEvent;

import java.util.List;

public interface UserEventService {

    UserEvent add(UserEvent userEvent);

    List<UserEvent> findAllWith(String name);

    UserEvent findById(Long id);

    UserEvent update(Long id, UserEvent userEvent);

    void delete(Long id);

}
