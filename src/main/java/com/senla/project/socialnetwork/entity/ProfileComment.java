package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Entity
@Table(name = "profile_comment")
public class ProfileComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "profile_owner_id")
    private Long profile_owner_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "comment_date")
    @Temporal(TemporalType.DATE)
    private Date comment_date;

    @Column(name = "comment_txt")
    private String comment_txt;

    public ProfileComment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfile_owner_id() {
        return profile_owner_id;
    }

    public void setProfile_owner_id(Long profile_owner_id) {
        this.profile_owner_id = profile_owner_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_txt() {
        return comment_txt;
    }

    public void setComment_txt(String comment_txt) {
        this.comment_txt = comment_txt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileComment that = (ProfileComment) o;
        return Objects.equals(id, that.id) && Objects.equals(profile_owner_id, that.profile_owner_id) && Objects.equals(user_id, that.user_id) && Objects.equals(comment_date, that.comment_date) && Objects.equals(comment_txt, that.comment_txt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profile_owner_id, user_id, comment_date, comment_txt);
    }
}
