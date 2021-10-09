package com.myproject.socialnetwork.repository;

import com.myproject.socialnetwork.entity.ProfileComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileCommentRepository extends JpaRepository<ProfileComment, Long>, JpaSpecificationExecutor<ProfileComment> {
}
