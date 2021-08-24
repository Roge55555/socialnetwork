package com.senla.project.socialnetwork.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageFilterRequest {

    Long mate_id;

    LocalDateTime from;

    LocalDateTime to;

}
