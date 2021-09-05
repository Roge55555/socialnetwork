package com.senla.project.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequestDTO {

    @NotBlank
    @Size(min = 4)
    private String login;

    @NotBlank
    @Size(min = 5)
    private String password;

}
