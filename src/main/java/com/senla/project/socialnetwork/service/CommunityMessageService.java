package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.model.filter.CommunityMessageFilterRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface CommunityMessageService {

    CommunityMessage add(String communityName, String txt);

    List<CommunityMessage> findAll(CommunityMessageFilterRequest request);

//    List<CommunityMessage> findCommunityMessagesByCommunityName(String communityName);
//
//    List<CommunityMessage> findCommunityMessagesByCommunityNameAndCreatorLogin(String communityName, String userLogin);
//
//    List<CommunityMessage> findCommunityMessagesByDateBetween(LocalDateTime from, LocalDateTime to);

    CommunityMessage findById(Long id);

    CommunityMessage update(Long id, String txt);

    void delete(Long id);

}
