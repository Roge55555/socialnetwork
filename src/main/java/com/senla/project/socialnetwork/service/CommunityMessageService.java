package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.model.filter.CommunityMessageFilterRequest;

import java.util.List;

public interface CommunityMessageService {

    CommunityMessage add(CommunityMessage communityMessage);

    List<CommunityMessage> findAll(CommunityMessageFilterRequest request);

    CommunityMessage findById(Long id);

    CommunityMessage update(Long id, String txt);

    void delete(Long id);

}
