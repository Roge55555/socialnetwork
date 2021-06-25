package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.ProfileComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileCommentRepository extends JpaRepository<ProfileComment, Long> {
}
