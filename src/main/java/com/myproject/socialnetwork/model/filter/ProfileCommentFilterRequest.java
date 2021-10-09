package com.myproject.socialnetwork.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileCommentFilterRequest {

    private Long ownerId;

    private Long userId;

    private LocalDateTime from;

    private LocalDateTime to;

}
