package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserOfCommunityRepository extends JpaRepository<UserOfCommunity, Long> {

    List<UserOfCommunity> findByUserIdOrderByCommunity(@NotBlank @Size(min = 4) Long userId);

    List<UserOfCommunity> findByCommunityIdOrderByUser(@Size(min = 3) @NotBlank Long communityId);

    Optional<UserOfCommunity> findByCommunityNameAndUserLogin(@Size(min = 3) @NotBlank String communityName, @NotBlank @Size(min = 4) String userLogin);

    Optional<UserOfCommunity> findByCommunityIdAndUserId(Long communityId, Long userId);

    void deleteByCommunityIdAndUserId(Long communityId, Long userId);

}
