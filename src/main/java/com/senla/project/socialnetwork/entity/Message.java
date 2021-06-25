package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public Long getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Long receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getMessage_txt() {
        return message_txt;
    }

    public void setMessage_txt(String message_txt) {
        this.message_txt = message_txt;
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
