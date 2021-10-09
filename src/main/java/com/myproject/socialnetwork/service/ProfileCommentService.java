package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.ProfileComment;
import com.myproject.socialnetwork.model.filter.ProfileCommentFilterRequest;

import java.util.List;

public interface ProfileCommentService {

    ProfileComment add(ProfileComment profileComment);

    List<ProfileComment> findAll(ProfileCommentFilterRequest request);

    ProfileComment findById(Long id);

    ProfileComment update(Long id, String txt);

    void delete(Long id);

}
