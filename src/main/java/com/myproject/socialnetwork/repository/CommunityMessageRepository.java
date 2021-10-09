package com.myproject.socialnetwork.repository;

import com.myproject.socialnetwork.entity.CommunityMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMessageRepository extends JpaRepository<CommunityMessage, Long>, JpaSpecificationExecutor<CommunityMessage> {
}
