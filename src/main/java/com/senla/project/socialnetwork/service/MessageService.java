package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.exeptions.NoSuchIdException;
import com.senla.project.socialnetwork.repository.MessageRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void add(Message message){
        messageRepository.save(message);
    }

    public List<Message> findAll(){
        return messageRepository.findAll();
    }

    @SneakyThrows
    public Message findById(Long id){
        return messageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public Message update(Long id, Message message) {

        return messageRepository.findById(id).map(mess -> {
            mess.setSender_id(message.getSender_id());
            mess.setReceiver_id(message.getReceiver_id());
            mess.setDate_created(message.getDate_created());
            mess.setMessage_txt(message.getMessage_txt());
            return messageRepository.save(mess);
        })
                .orElseThrow(() ->
                        new NoSuchIdException(id, "message")
                );
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
}
