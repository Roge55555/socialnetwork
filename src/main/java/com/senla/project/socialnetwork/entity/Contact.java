package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "contact")
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
    private Boolean contactLevel;//isAccepted

    @ManyToOne
    @JoinColumn(name = "contact_role")
    private RoleList contactRole;

}
