package com.senla.project.socialnetwork.model.filter;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageFilterRequest {

    Long mateId;

    LocalDateTime from;

    LocalDateTime to;

}
