package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.ProfileComment;

import java.util.List;

public interface ProfileCommentService {

    ProfileComment add(ProfileComment profileComment);

    List<ProfileComment> findAll();

    ProfileComment findById(Long id);

    ProfileComment update(Long id, ProfileComment profileComment);

    void delete(Long id);

}
