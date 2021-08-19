package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.model.dto.UserOfCommunityDTO;

import java.util.List;

public interface UserOfCommunityService {

    UserOfCommunity add(Long communityId, Long userId);

    List<UserOfCommunity> findAllCommunitiesOfUser(String userLogin);

    List<UserOfCommunity> findAllUsersOfCommunity(String communityName);

    UserOfCommunity findByCommunityNameAndUserLogin(String communityName, String userLogin);

    void delete(Long communityId, Long userId);

}
