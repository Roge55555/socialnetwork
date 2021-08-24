package com.senla.project.socialnetwork.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityMessageFilterRequest {

    Long community_id;

    Long user_id;

    LocalDateTime from;

    LocalDateTime to;

}
