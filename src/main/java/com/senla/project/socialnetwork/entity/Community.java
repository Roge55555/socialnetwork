package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Data
@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creator_id")
    private Long creator_id;

    @Column(name = "name")
    @Size(min = 3)
    @NotBlank
    private String name;

    @Column(name = "description")
    @Size(min = 10)
    private String description;

    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date date_created;

    public Community() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Community community = (Community) o;
        return Objects.equals(id, community.id) && Objects.equals(creator_id, community.creator_id) && Objects.equals(name, community.name) && Objects.equals(description, community.description) && Objects.equals(date_created, community.date_created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator_id, name, description, date_created);
    }
}
