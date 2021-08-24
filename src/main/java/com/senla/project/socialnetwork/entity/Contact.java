package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "contact", uniqueConstraints = @UniqueConstraint(columnNames = {"creator_id", "mate_id"}))
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "mate_id")
    private User mate;

    @Column(name = "date_connected")
    private LocalDate dateConnected;

    @Column(name = "contact_level")
    private Boolean contactLevel; //TODO isAccepted

    @ManyToOne
    @JoinColumn(name = "contact_role")
    private RoleList creatorRole;

    @ManyToOne
    @JoinColumn(name = "contact_role")
    private RoleList mateRole;

}
