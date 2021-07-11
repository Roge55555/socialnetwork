package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;

    public void add(Message message) {
        messageRepository.save(message);
    }

    public List<Message> findAll() {
        if (messageRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return messageRepository.findAll();
    }

    public Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Message update(Long id, Message message) {

        return messageRepository.findById(id).map(mess -> {
            mess.setSender(message.getSender());
            mess.setReceiver(message.getReceiver());
            mess.setDateCreated(message.getDateCreated());
            mess.setMessageTxt(message.getMessageTxt());
            return messageRepository.save(mess);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (messageRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        messageRepository.deleteById(id);
    }
}