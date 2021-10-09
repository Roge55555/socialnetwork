package com.myproject.socialnetwork.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommunityMessageFilterRequest {

    private Long communityId;

    private Long userId;

    private LocalDateTime from;

    private LocalDateTime to;

}
