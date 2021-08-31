package com.senla.project.socialnetwork.model.filter;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ContactFilterRequest {

    Long mateId;

    Boolean level;

    Long myRoleId;

    Long mateRoleId;

    LocalDate from;

    LocalDate to;

}