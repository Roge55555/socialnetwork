package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sender_id")
    private Long sender_id;

    @Column(name = "receiver_id")
    private Long receiver_id;

    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date date_created;

    @Column(name = "message_txt")
    private String message_txt;

    public Message() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(sender_id, message.sender_id) && Objects.equals(receiver_id, message.receiver_id) && Objects.equals(date_created, message.date_created) && Objects.equals(message_txt, message.message_txt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender_id, receiver_id, date_created, message_txt);
    }
}
