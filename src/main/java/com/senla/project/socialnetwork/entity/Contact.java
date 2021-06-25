package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creator_id")
    private Long creator_id;

    @Column(name = "mate_id")
    private Long mate_id;

    @Column(name = "date_connected")
    @Temporal(TemporalType.DATE)
    private Date date_connected;

    @Column(name = "contact_level")
    private Boolean contact_level;

    @Column(name = "contact_role")
    private Long contact_role;

    public Contact() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
    }

    public Long getMate_id() {
        return mate_id;
    }

    public void setMate_id(Long mate_id) {
        this.mate_id = mate_id;
    }

    public Date getDate_connected() {
        return date_connected;
    }

    public void setDate_connected(Date date_connected) {
        this.date_connected = date_connected;
    }

    public Boolean getContact_level() {
        return contact_level;
    }

    public void setContact_level(Boolean contact_level) {
        this.contact_level = contact_level;
    }

    public Long getContact_role() {
        return contact_role;
    }

    public void setContact_role(Long contact_role) {
        this.contact_role = contact_role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) && Objects.equals(creator_id, contact.creator_id) && Objects.equals(mate_id, contact.mate_id) && Objects.equals(date_connected, contact.date_connected) && Objects.equals(contact_level, contact.contact_level) && Objects.equals(contact_role, contact.contact_role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator_id, mate_id, date_connected, contact_level, contact_role);
    }
}
