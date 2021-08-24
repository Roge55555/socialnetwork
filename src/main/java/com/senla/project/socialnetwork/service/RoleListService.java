package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;

import java.util.List;

public interface RoleListService {

    RoleList add(RoleList roleList);

    List<RoleList> findAllWith(String name);

    RoleList findById(Long id);

    RoleList update(Long id, RoleList roleList);

    void delete(Long id);

}
