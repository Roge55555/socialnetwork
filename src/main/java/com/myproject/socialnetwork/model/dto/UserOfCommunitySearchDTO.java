package com.myproject.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOfCommunitySearchDTO {

    private Long communityId;

    private Long userId;

}
