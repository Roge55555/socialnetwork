package com.senla.project.socialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "name")
    @Size(min = 3)
    @NotBlank
    private String name;

    @Column(name = "description")
    @Size(min = 10)
    private String description;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    private List<CommunityMessage> communityMessageCommunitySet;

    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    private List<UserOfCommunity> userOfCommunityCommunitySet;

    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    private List<Blocklist> blocklistCommunitySet;

}
