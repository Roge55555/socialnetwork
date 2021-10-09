package com.myproject.socialnetwork.repository;

import com.myproject.socialnetwork.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findAllByNameContaining(String name);

    Optional<Community> findByNameAndCreatorLogin(@Size(min = 3) @NotBlank String name, @NotBlank @Size(min = 4) String creatorLogin);

    Optional<Community> findByIdAndCreatorLogin(Long id, @NotBlank @Size(min = 4) String creatorLogin);

    List<Community> findAllByCreatorLoginOrderByDateCreated(@NotBlank @Size(min = 4) String creatorLogin);

}
