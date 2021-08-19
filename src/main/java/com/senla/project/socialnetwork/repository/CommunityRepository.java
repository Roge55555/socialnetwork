package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByNameAndCreator(String name, User creator);

    List<Community> findAllByCreatorOrderByDateCreated(User creator);

}
