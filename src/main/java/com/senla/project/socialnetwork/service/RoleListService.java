package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;

import java.util.List;

public interface RoleListService {

    RoleList add(String roleListName);

    List<RoleList> findAllWith(String name);

    RoleList findById(Long id);

    RoleList update(Long id, String roleListName);

    void delete(Long id);

}
