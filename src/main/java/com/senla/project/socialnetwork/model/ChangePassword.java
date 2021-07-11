package com.senla.project.socialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class ChangePassword {


    @Column
    private String oldPassword;

    @Column
    private String newPassword;
}
