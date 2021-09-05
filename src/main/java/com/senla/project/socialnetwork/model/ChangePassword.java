package com.senla.project.socialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
