package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.model.filter.MessageFilterRequest;

import java.util.List;

public interface MessageService {

    Message add(Long userId, String txt);

    List<Message> findAll(MessageFilterRequest request);

    Message findById(Long id);

    Message update(Long id, String txt);

    void delete(Long id);

}
