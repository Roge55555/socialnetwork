package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.MessageRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public Message add(Message message) {
        LOGGER.info("Trying to add message.");

        if (userRepository.findById(message.getSender().getId()).isEmpty() ||
                userRepository.findById(message.getReceiver().getId()).isEmpty()) {
            LOGGER.error("Sender/Receiver do(es)n`t exist");
            throw new NoSuchElementException();
        }
        message.setId(null);
        final Message save = messageRepository.save(message);
        LOGGER.info("Message added.");
        return save;
    }

    @Override
    public List<Message> findAll() {
        LOGGER.info("Trying to show all messages.");
        if (userRepository.findAll().isEmpty()) {
            LOGGER.warn("Message`s list is empty!");
        } else {
            LOGGER.info("Message(s) found.");
        }
        return messageRepository.findAll();
    }

    @Override
    public Message findById(Long id) {
        LOGGER.info("Trying to find message by id");
        final Message message = messageRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Message found using id {}", message.getId());
        return message;
    }

    @Override
    public Message update(Long id, Message message) {
        LOGGER.info("Trying to update message with id - {}.", id);
        if (userRepository.findById(message.getSender().getId()).isEmpty() ||
                userRepository.findById(message.getReceiver().getId()).isEmpty()) {
            LOGGER.error("Sender/Receiver do(es)n`t exist");
            throw new NoSuchElementException(id);
        }


        return messageRepository.findById(id).map(mess -> {
            mess.setSender(message.getSender());
            mess.setReceiver(message.getReceiver());
            mess.setDateCreated(message.getDateCreated());
            mess.setMessageTxt(message.getMessageTxt());
            final Message save = messageRepository.save(mess);
            LOGGER.info("Message with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete message with id - {}.", id);
        if (messageRepository.findById(id).isEmpty()) {
            LOGGER.error("No message with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        messageRepository.deleteById(id);
        LOGGER.info("Message with id - {} was deleted.", id);
    }
}
