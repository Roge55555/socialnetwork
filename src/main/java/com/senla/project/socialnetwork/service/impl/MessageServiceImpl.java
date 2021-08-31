package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.filter.MessageFilterRequest;
import com.senla.project.socialnetwork.repository.MessageRepository;
import com.senla.project.socialnetwork.repository.specification.MessageSpecification;
import com.senla.project.socialnetwork.service.MessageService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public Message add(String userLogin, String txt) {
        LOGGER.info("Trying to add message.");

        if (userService.findByLogin(userLogin) == null) {
            LOGGER.error("Receiver do(es)n`t exist");
            throw new NoSuchElementException();
        }
        Message message = Message.builder()
                .sender(userService.findByLogin(Utils.getLogin()))
                .receiver(userService.findByLogin(userLogin))
                .dateCreated(LocalDateTime.now())
                .messageTxt(txt)
                .build();
        final Message save = messageRepository.save(message);
        LOGGER.info("Message added.");
        return save;
    }


//    @Override
//    public List<Message> findAllMessagesWith(String userLogin) {
//
//        LOGGER.info("Trying to show all messages with {}.", userLogin);
//
//        if (messageRepository.findAllByReceiverLoginAndSenderLoginOrReceiverLoginAndSenderLoginOrderByDateCreated(
//                Utils.getLogin(), userLogin, userLogin, Utils.getLogin()).isEmpty()) {
//            LOGGER.warn("Message`s list is empty!");
//        } else {
//            LOGGER.info("Message(s) found.");
//        }
//        return messageRepository.findAllByReceiverLoginAndSenderLoginOrReceiverLoginAndSenderLoginOrderByDateCreated(
//                Utils.getLogin(), userLogin, userLogin, Utils.getLogin());
//    }
//
//    @Override
//    public List<Message> findAllMessagesWithBetween(String userLogin, LocalDateTime from, LocalDateTime to) {
//
//        LOGGER.info("Trying to show all messages with {} from {} to {}.", userLogin, from, to);
//
//        if (messageRepository.findAllByReceiverLoginAndSenderLoginOrReceiverLoginAndSenderLoginAndDateCreatedBetweenOrderByDateCreated(
//                Utils.getLogin(), userLogin, userLogin, Utils.getLogin(), from, to).isEmpty()) {
//            LOGGER.warn("Message`s list is empty!");
//        } else {
//            LOGGER.info("Message(s) found.");
//        }
//        return messageRepository.findAllByReceiverLoginAndSenderLoginOrReceiverLoginAndSenderLoginAndDateCreatedBetweenOrderByDateCreated(
//                Utils.getLogin(), userLogin, userLogin, Utils.getLogin(), from, to);
//    }

    @Override
    public List<Message> findAll(MessageFilterRequest request) {

        return messageRepository.findAll(MessageSpecification.getSpecification(request), Sort.by("dateCreated"));
    }

    @Override
    public Message findById(Long id) {
        LOGGER.info("Trying to find message by id");
        final Message message = messageRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("Message found using id {}", message.getId());
        return message;
    }

    @Override
    public Message update(Long id, String txt) {
        LOGGER.info("Trying to update message with id - {}.", id);
        if (!userService.findByLogin(Utils.getLogin()).equals(
                messageRepository.findById(id).get().getSender())) {
            LOGGER.error("Trying to update not your message.");
            throw new TryingModifyNotYourDataException("This is not your message!");
        }

        return messageRepository.findById(id).map(mess -> {
            mess.setDateCreated(LocalDateTime.now());
            mess.setMessageTxt(txt);
            final Message save = messageRepository.save(mess);
            LOGGER.info("Message with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    throw new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete message with id - {}.", id);
        if (!userService.findByLogin(Utils.getLogin()).equals(
                messageRepository.findById(id).get().getSender())) {
            LOGGER.error("Trying to delete not your message.");
            throw new TryingModifyNotYourDataException("This is not your message!");
        }
        messageRepository.deleteById(id);
        LOGGER.info("Message with id - {} was deleted.", id);
    }

}
