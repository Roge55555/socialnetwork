package com.senla.project.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOfCommunityDTO {

    private Long communityId;

    private Long userId;

}