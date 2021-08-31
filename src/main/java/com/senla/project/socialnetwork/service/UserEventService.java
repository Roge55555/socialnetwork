package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserEvent;
import liquibase.pro.packaged.S;

import java.time.LocalDateTime;
import java.util.List;

public interface UserEventService {

    UserEvent add(String name, String description, LocalDateTime dateTime);

    List<UserEvent> findAllWith(String name);

    UserEvent findById(Long id);

    UserEvent update(Long id, String name, String description, LocalDateTime dateTime);

    void delete(Long id);

}
