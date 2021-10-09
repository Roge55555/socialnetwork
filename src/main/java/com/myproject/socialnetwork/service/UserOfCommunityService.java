package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.UserOfCommunity;

import java.util.List;
import java.util.Optional;

public interface UserOfCommunityService {

    UserOfCommunity add(UserOfCommunity userOfCommunity);

    List<UserOfCommunity> findAllCommunitiesOfUser();

    List<UserOfCommunity> findAllUsersOfCommunity(Long communityId);

    Optional<UserOfCommunity> findByCommunityNameAndUserLogin(String communityName, String userLogin);

    UserOfCommunity findByCommunityIdAndUserId(Long communityId, Long userId);

    void delete(Long communityId, Long userId);

}
