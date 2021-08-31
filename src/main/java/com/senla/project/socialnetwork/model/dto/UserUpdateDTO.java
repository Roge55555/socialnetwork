package com.senla.project.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDTO {

    @NotBlank
    @NotNull
    @Size(min = 4)
    private String login;

    @Past
    private LocalDate dateBirth;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 7, max = 13)
    private String phone;

    private String website;

    private String aboutYourself;

    private String jobTitle;

    @Size(min = 6, max = 13)
    private String workPhone;

}
