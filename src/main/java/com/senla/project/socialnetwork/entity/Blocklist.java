package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Data
@Entity
@Table(name = "blocklist")
public class Blocklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "community_id")
    private Long community_id;

    @Column(name = "who_baned")
    private Long who_baned;

    @Column(name = "whom_baned")
    private Long whom_baned;

    @Column(name = "block_date")
    @Temporal(TemporalType.DATE)
    private Date block_date;

    @Column(name = "block_cause")
    private String block_cause;

    public Blocklist() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blocklist blocklist = (Blocklist) o;
        return Objects.equals(id, blocklist.id) && Objects.equals(community_id, blocklist.community_id) && Objects.equals(who_baned, blocklist.who_baned) && Objects.equals(whom_baned, blocklist.whom_baned) && Objects.equals(block_date, blocklist.block_date) && Objects.equals(block_cause, blocklist.block_cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, community_id, who_baned, whom_baned, block_date, block_cause);
    }
}
