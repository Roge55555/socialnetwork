package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOfCommunityRepository extends JpaRepository<UserOfCommunity, Long> {

    List<UserOfCommunity> findByUserLoginOrderByCommunity(String userLogin);

    List<UserOfCommunity> findByCommunityNameOrderByUser(String communityName);

    Optional<UserOfCommunity> findByCommunityNameAndUserLogin(String communityName, String userLogin);

    void deleteByCommunityIdAndUserId(Long communityId, Long userId);

}
