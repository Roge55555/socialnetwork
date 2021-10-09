package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.Community;

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
