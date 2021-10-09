package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.Message;
import com.myproject.socialnetwork.model.filter.MessageFilterRequest;

import java.util.List;

public interface MessageService {

    Message add(Long userId, String txt);

    List<Message> findAll(MessageFilterRequest request);

    Message findById(Long id);

    Message update(Long id, String txt);

    void delete(Long id);

}
