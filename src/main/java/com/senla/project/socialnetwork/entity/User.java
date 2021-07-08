package com.senla.project.socialnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
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
    private Date dateBirth;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "email")
    @NotBlank
    @Email
    private String email;

    @Column(name = "phone")
    @NotBlank
    @Size(min = 7, max = 13)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role")
    private AccessRole role;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column(name = "website")
    private String website;

    @Column(name = "about_yourself")
    private String aboutYourself;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "work_phone")
    @Size(min = 6, max = 13)
    private String workPhone;

}
