package com.senla.project.socialnetwork.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactFilterRequest {

    private Long mateId;

    private Boolean level;

    private Long myRoleId;

    private Long mateRoleId;

    private LocalDate from;

    private LocalDate to;

}
