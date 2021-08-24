package com.senla.project.socialnetwork.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactFilterRequest {

    Long mate_id;

    Boolean level;

    Long my_role_id;

    Long mate_role_id;

    LocalDateTime from;

    LocalDateTime to;

}
