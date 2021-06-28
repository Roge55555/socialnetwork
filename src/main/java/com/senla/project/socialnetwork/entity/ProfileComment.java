package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Data
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
