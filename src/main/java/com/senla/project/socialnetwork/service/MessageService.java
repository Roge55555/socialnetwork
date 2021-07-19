package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.MessageRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    public Message add(Message message) {
        if(userRepository.findById(message.getSender().getId()).isEmpty() ||
                userRepository.findById(message.getReceiver().getId()).isEmpty())
            throw new NoSuchElementException();
        message.setId(null);
        return messageRepository.save(message);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Message update(Long id, Message message) {

        if(userRepository.findById(message.getSender().getId()).isEmpty() ||
                userRepository.findById(message.getReceiver().getId()).isEmpty())
            throw new NoSuchElementException();

        return messageRepository.findById(id).map(mess -> {
            mess.setSender(message.getSender());
            mess.setReceiver(message.getReceiver());
            mess.setDateCreated(message.getDateCreated());
            mess.setMessageTxt(message.getMessageTxt());
            return messageRepository.save(mess);
        })
                .orElseThrow(() -> new NoSuchElementException(id));
    }

    public void delete(Long id) {
        if (messageRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException(id);
        }
        messageRepository.deleteById(id);
    }
}
