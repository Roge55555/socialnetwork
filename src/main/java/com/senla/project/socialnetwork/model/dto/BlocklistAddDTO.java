package com.senla.project.socialnetwork.model.dto;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlocklistAddDTO {

    private Community community; //TODO Long communityId

    private User whomBaned; //TODO Long userId

    private String blockCause;

}
