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
@Table(name = "user_of_community")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 8)
    private String name;

    @Column(name = "description")
    @NotBlank
    @Size(min = 13)
    private String description;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public UserEvent() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEvent userEvent = (UserEvent) o;
        return Objects.equals(id, userEvent.id) && Objects.equals(user_id, userEvent.user_id) && Objects.equals(name, userEvent.name) && Objects.equals(description, userEvent.description) && Objects.equals(date, userEvent.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, name, description, date);
    }
}
