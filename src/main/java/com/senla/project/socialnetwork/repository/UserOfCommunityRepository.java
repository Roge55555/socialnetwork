package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOfCommunityRepository extends JpaRepository<UserOfCommunity, Long> {
}
