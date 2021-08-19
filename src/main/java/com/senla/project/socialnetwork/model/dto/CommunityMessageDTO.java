package com.senla.project.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommunityMessageDTO {

    private String communityName;

    @NotBlank
    private String txt;

}
