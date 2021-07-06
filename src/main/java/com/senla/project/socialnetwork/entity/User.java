package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    @NotBlank
    @Size(min = 5)
    private String login;

    @Column(name = "password")
    @NotBlank
    @Size(min = 8)
    private String password;

    @Column(name = "date_birth")
    @Temporal(TemporalType.DATE)
    @Past
    private Date date_birth;

    @Column(name = "first_name")
    @NotBlank
    private String first_name;

    @Column(name = "last_name")
    @NotBlank
    private String last_name;

    @Column(name = "email")
    @NotBlank
    @Email
    private String email;

    @Column(name = "phone")
    @NotBlank
    @Size(min = 7, max = 13)
    private String phone;

    @Column(name = "role")
    private Long role;

    @Column(name = "is_active")
    private Boolean is_active;

    @Column(name = "is_blocked")
    private Boolean is_blocked;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registration_date;

    @Column(name = "website")
    private String website;

    @Column(name = "about_yourself")
    private String about_yourself;

    @Column(name = "job_title")
    private String job_title;

    @Column(name = "work_phone")
    @Size(min = 6, max = 13)
    private String work_phone;

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(date_birth, user.date_birth) && Objects.equals(first_name, user.first_name) && Objects.equals(last_name, user.last_name) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(role, user.role) && Objects.equals(is_active, user.is_active) && Objects.equals(is_blocked, user.is_blocked) && Objects.equals(registration_date, user.registration_date) && Objects.equals(website, user.website) && Objects.equals(about_yourself, user.about_yourself) && Objects.equals(job_title, user.job_title) && Objects.equals(work_phone, user.work_phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, date_birth, first_name, last_name, email, phone, role, is_active, is_blocked, registration_date, website, about_yourself, job_title, work_phone);
    }
}
