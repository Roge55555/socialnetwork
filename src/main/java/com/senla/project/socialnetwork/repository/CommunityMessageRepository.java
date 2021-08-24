package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommunityMessageRepository extends JpaRepository<CommunityMessage, Long>, JpaSpecificationExecutor<CommunityMessage> {

    List<CommunityMessage> findCommunityMessagesByCommunityNameOrderByDate(String communityName);

    List<CommunityMessage> findCommunityMessagesByCommunityNameAndCreatorLoginOrderByDate(String communityName, String userLogin);

    List<CommunityMessage> findCommunityMessagesByDateBetweenOrderByDate(LocalDateTime from, LocalDateTime to);

}
