package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;

import java.util.List;

public interface MessageService{

    Message add(Message message);

    List<Message> findAll();

    Message findById(Long id);

    Message update(Long id, Message message);

    void delete(Long id);

}
