package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    Message add(String userLogin, String txt);

    List<Message> findAllMessagesWith(String userLogin);

    List<Message> findAllMessagesWithBetween(String userLogin, LocalDateTime from, LocalDateTime to);

    Message findById(Long id);

    Message update(Long id, String txt);

    void delete(Long id);

}
