package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;

import java.util.List;


public interface CommunityService {

    Community add(Community community);

    List<Community> findAll();

    Community findById(Long Id);

    Community findByName(String name);

    Community update(Long id, Community community);

    void delete(Long id);

}
