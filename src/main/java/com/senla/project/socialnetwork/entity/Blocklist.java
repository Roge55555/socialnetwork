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
@Table(name = "blocklist",uniqueConstraints = @UniqueConstraint(columnNames = {"community_id", "who_baned", "whom_baned"}) )
public class Blocklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "who_baned")
    private User whoBaned;

    @ManyToOne
    @JoinColumn(name = "whom_baned")
    private User whomBaned;

    @Column(name = "block_date")
    private LocalDate blockDate;

    @Column(name = "block_cause")
    private String blockCause;

}
