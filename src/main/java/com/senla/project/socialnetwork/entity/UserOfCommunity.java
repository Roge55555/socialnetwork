package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Entity
@Table(name = "user_of_community")
public class UserOfCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "community_id")
    private Long community_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "date_entered")
    @Temporal(TemporalType.DATE)
    private Date date_entered;

    public UserOfCommunity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Long community_id) {
        this.community_id = community_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getDate_entered() {
        return date_entered;
    }

    public void setDate_entered(Date date_entered) {
        this.date_entered = date_entered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOfCommunity that = (UserOfCommunity) o;
        return Objects.equals(id, that.id) && Objects.equals(community_id, that.community_id) && Objects.equals(user_id, that.user_id) && Objects.equals(date_entered, that.date_entered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, community_id, user_id, date_entered);
    }
}
