package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserOfCommunity;

import java.util.List;

public interface UserOfCommunityService {

    UserOfCommunity add(UserOfCommunity userOfCommunity);

    List<UserOfCommunity> findAllCommunitiesOfUser();

    List<UserOfCommunity> findAllUsersOfCommunity(Long communityId);

    UserOfCommunity findByCommunityNameAndUserLogin(String communityName, String userLogin);

    UserOfCommunity findByCommunityIdAndUserId(Long communityId, Long userId);

    void delete(Long communityId, Long userId);

}
