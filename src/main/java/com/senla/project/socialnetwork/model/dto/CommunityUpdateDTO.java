package com.senla.project.socialnetwork.model.dto;

import com.senla.project.socialnetwork.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommunityUpdateDTO extends CommunityAddDTO {

    private User creator; //TODO Long creatorId

}
