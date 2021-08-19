package com.senla.project.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOfCommunitySearchDTO {

    private String communityName;

    private String userLogin;

}
