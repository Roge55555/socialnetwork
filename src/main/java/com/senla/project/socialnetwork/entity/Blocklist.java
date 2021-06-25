package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
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

    public Long getWho_baned() {
        return who_baned;
    }

    public void setWho_baned(Long who_baned) {
        this.who_baned = who_baned;
    }

    public Long getWhom_baned() {
        return whom_baned;
    }

    public void setWhom_baned(Long whom_baned) {
        this.whom_baned = whom_baned;
    }

    public Date getBlock_date() {
        return block_date;
    }

    public void setBlock_date(Date block_date) {
        this.block_date = block_date;
    }

    public String getBlock_cause() {
        return block_cause;
    }

    public void setBlock_cause(String block_cause) {
        this.block_cause = block_cause;
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
