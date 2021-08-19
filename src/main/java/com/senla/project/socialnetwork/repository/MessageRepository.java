package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByReceiverLoginAndSenderLoginOrReceiverLoginAndSenderLoginOrderByDateCreated(String receiver_id, String sender_id, String receiver_id2, String sender_id2);

    List<Message> findAllByReceiverLoginAndSenderLoginOrReceiverLoginAndSenderLoginAndDateCreatedBetweenOrderByDateCreated(String receiver_id, String sender_id, String receiver_id2, String sender_id2, LocalDateTime from, LocalDateTime to);

}
