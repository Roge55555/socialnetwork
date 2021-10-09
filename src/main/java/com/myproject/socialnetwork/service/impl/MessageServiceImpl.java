package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.repository.MessageRepository;
import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.Message;
import com.myproject.socialnetwork.entity.Message_;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.model.filter.MessageFilterRequest;
import com.myproject.socialnetwork.repository.specification.MessageSpecification;
import com.myproject.socialnetwork.service.MessageService;
import com.myproject.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public Message add(Long userId, String txt) {
        return messageRepository.save(
                Message.builder()
                        .sender(userService.findByLogin(Utils.getLogin()))
                        .receiver(userService.findById(userId))
                        .dateCreated(LocalDateTime.now().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS))
                        .messageTxt(txt)
                        .build());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Message> findAll(MessageFilterRequest request) {
        return messageRepository.findAll(MessageSpecification.getSpecification(request), Sort.by(Message_.DATE_CREATED));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public Message findById(Long id) {

        final Message message = messageRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });

        if (!Utils.getLogin().equals(message.getSender().getLogin()) &&
                !Utils.getLogin().equals(message.getReceiver().getLogin())) {
            LOGGER.error("Trying to find message in not your dialog.");
            throw new TryingModifyNotYourDataException("This is not your dialog!");
        }

        return message;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
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

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void delete(Long id) {
        if (!Utils.getLogin().equals(findById(id).getSender().getLogin())) {
            LOGGER.error("Trying to delete not your message.");
            throw new TryingModifyNotYourDataException("This is not your message!");
        }

        messageRepository.deleteById(id);
    }

}
