package com.senla.project.socialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user")
public class User {

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
    @ToString.Exclude
    private List<Blocklist> whoBanedSet;

    @JsonIgnore
    @OneToMany(mappedBy = "whomBaned", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Blocklist> whomBanedSet;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Contact> contactCreatorSet;

    @JsonIgnore
    @OneToMany(mappedBy = "mate", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Contact> contactMateSet;

    @JsonIgnore
    @OneToMany(mappedBy = "profileOwner", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProfileComment> profileOwnerSet;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProfileComment> userProfileCommentSet;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<UserEvent> userEventSet;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Community> communityCreatorSet;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CommunityMessage> communityMessageCreatorSet;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<UserOfCommunity> userOfCommunityUserSet;

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Message> messageSenderSet;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Message> messageReceiverSet;

}
