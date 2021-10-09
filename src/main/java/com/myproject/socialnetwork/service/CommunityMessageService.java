package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.CommunityMessage;
import com.myproject.socialnetwork.model.filter.CommunityMessageFilterRequest;

import java.util.List;

public interface CommunityMessageService {

    CommunityMessage add(CommunityMessage communityMessage);

    List<CommunityMessage> findAll(CommunityMessageFilterRequest request);

    CommunityMessage findById(Long id);

    CommunityMessage update(Long id, String txt);

    void delete(Long id);

}
