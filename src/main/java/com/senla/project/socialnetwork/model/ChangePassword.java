package com.senla.project.socialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ChangePassword {

    @Column
    @NotBlank
    @Size(min = 5)
    private String oldPassword;

    @Column
    @NotBlank
    @Size(min = 5)
    private String newPassword;

}
