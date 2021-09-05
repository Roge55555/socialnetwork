package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.entity.Message_;
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
    public Message add(Long userId, String txt) {
        return messageRepository.save(
                Message.builder()
                .sender(userService.findByLogin(Utils.getLogin()))
                .receiver(userService.findById(userId))
                .dateCreated(LocalDateTime.now())
                .messageTxt(txt)
                .build());
    }

    @Override
    public List<Message> findAll(MessageFilterRequest request) {
        return messageRepository.findAll(MessageSpecification.getSpecification(request), Sort.by(Message_.DATE_CREATED));
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public Message update(Long id, String txt) {
        if (!Utils.getLogin().equals(findById(id).getSender().getLogin())) {
            LOGGER.error("Trying to update not your message.");
            throw new TryingModifyNotYourDataException("This is not your message!");
        }

        Message message = findById(id);
        message.setMessageTxt(txt);
        return messageRepository.save(message);
    }

    @Override
    public void delete(Long id) {
        if (!Utils.getLogin().equals(findById(id).getSender().getLogin())) {
            LOGGER.error("Trying to delete not your message.");
            throw new TryingModifyNotYourDataException("This is not your message!");
        }

        messageRepository.deleteById(id);
    }

}
