package com.senla.project.socialnetwork.model.filter;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityMessageFilterRequest {

    Long communityId;

    Long userId;

    LocalDateTime from;

    LocalDateTime to;

}
