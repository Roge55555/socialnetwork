package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.AccessRole;
import com.myproject.socialnetwork.model.Role;

public interface AccessRoleService {

    AccessRole findById(Long id);

    AccessRole findByName(Role name);

}
