package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.model.filter.ProfileCommentFilterRequest;

import java.util.List;

public interface ProfileCommentService {

    ProfileComment add(Long ownerId, String txt);

    List<ProfileComment> findAll(ProfileCommentFilterRequest request);

    ProfileComment findById(Long id);

    ProfileComment update(Long id, String txt);

    void delete(Long id);

}
