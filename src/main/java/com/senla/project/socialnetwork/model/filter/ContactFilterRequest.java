package com.senla.project.socialnetwork.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactFilterRequest {

    Long mateId;

    Boolean level;

    Long myRoleId;

    Long mateRoleId;

    LocalDate from;

    LocalDate to;

}
