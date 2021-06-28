package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Data
@Entity
@Table(name = "community_message")
public class CommunityMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creator_id")
    private Long creator_id;

    @Column(name = "community_id")
    private Long community_id;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "txt")
    private String txt;

    public CommunityMessage() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityMessage that = (CommunityMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(creator_id, that.creator_id) && Objects.equals(community_id, that.community_id) && Objects.equals(date, that.date) && Objects.equals(txt, that.txt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator_id, community_id, date, txt);
    }
}
