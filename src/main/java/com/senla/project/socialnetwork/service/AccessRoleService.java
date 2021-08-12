package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.model.Role;

public interface AccessRoleService {

    AccessRole findById(Long id);

    AccessRole findByName(Role name);
}

