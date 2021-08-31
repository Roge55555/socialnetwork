package com.senla.project.socialnetwork.model.filter;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileCommentFilterRequest {

    Long ownerId;

    Long userId;

    LocalDateTime from;

    LocalDateTime to;

}
