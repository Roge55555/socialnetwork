package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.model.Role;
import com.senla.project.socialnetwork.repository.AccessRoleRepository;
import com.senla.project.socialnetwork.service.AccessRoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessRoleServiceImpl implements AccessRoleService {

    private final AccessRoleRepository accessRoleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessRoleServiceImpl.class);

    @Override
    public AccessRole findById(Long id) {
        return accessRoleRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public AccessRole findByName(Role name) {
        return accessRoleRepository.findByName(name).orElseThrow(() -> {
            LOGGER.error("No element with such login - {}.", name);
            throw new NoSuchElementException(name.name());
        });
    }

}
