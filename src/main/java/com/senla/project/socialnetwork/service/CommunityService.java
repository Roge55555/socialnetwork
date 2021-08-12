package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CommunityService {

    Community add(Community community);

    List<Community> findAll();

    Page<Community> findAll(Pageable pageable);

    Community findById(Long id);

    Community update(Long id, Community community);

    void delete(Long id);

}
