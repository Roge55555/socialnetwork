package com.senla.project.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlocklistAddDTO {

    private Long communityId;

    private Long whomBanedId;

    private String blockCause;

}
