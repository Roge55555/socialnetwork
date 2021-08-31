package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.model.filter.MessageFilterRequest;

import java.util.List;

public interface MessageService {

    Message add(String userLogin, String txt);

    List<Message> findAll(MessageFilterRequest request);

//    List<Message> findAllMessagesWith(String userLogin);
//
//    List<Message> findAllMessagesWithBetween(String userLogin, LocalDateTime from, LocalDateTime to);

    Message findById(Long id);

    Message update(Long id, String txt);

    void delete(Long id);

}
