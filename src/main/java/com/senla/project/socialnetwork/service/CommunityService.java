package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;

import java.util.List;


public interface CommunityService {

    Community add(Community community);

    List<Community> findAll();

    Community findById(Long id);

    Community findByName(String name);

    List<Community> searchBySubstringOfName(String name);

    Community update(Long id, Community community);

    void delete(Long id);

}
