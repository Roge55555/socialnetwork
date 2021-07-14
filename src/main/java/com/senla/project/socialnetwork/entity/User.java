package com.senla.project.socialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", unique = true)
    @NotBlank
    @Size(min = 4)
    private String login;

    @Column(name = "password")
    @NotBlank
    @Size(min = 5)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "date_birth")
    @Past
    private LocalDate dateBirth;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(name = "phone", unique = true)
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
    private LocalDate registrationDate;

    @Column(name = "website")
    private String website;

    @Column(name = "about_yourself")
    private String aboutYourself;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "work_phone")
    @Size(min = 6, max = 13)
    private String workPhone;

    @JsonIgnore
    @OneToMany(mappedBy = "whoBaned", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    private List<Blocklist> whoBanedSet;

    @JsonIgnore
    @OneToMany(mappedBy = "whomBaned", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    private List<Blocklist> whomBanedSet;

}
