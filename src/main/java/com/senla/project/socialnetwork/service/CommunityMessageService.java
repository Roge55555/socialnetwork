package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.CommunityMessage;

import java.util.List;

public interface CommunityMessageService {

    CommunityMessage add(CommunityMessage communityMessage);

    List<CommunityMessage> findAll();

    CommunityMessage findById(Long id);

    CommunityMessage update(Long id, CommunityMessage communityMessage);

    void delete(Long id);

}
