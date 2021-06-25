package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@Entity
@Table(name = "role_list")
public class RoleList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public RoleList() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole_name() {
        return name;
    }

    public void setRole_name(String role_name) {
        this.name = role_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleList that = (RoleList) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
