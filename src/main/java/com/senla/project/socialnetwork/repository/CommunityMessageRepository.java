package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMessageRepository extends JpaRepository<CommunityMessage, Long> {
}
