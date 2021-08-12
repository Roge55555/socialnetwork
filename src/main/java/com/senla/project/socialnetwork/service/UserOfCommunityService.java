package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserOfCommunity;

import java.util.List;

public interface UserOfCommunityService {

    UserOfCommunity add(UserOfCommunity userOfCommunity);

    List<UserOfCommunity> findAll();

    UserOfCommunity findById(Long id);

    void delete(Long id);
}
