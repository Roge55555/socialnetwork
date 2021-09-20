package com.senla.project.socialnetwork.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageFilterRequest {

    private Long mateId;

    private LocalDateTime from;

    private LocalDateTime to;

}
