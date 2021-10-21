package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.entity.AccessRole;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.model.Role;
import com.myproject.socialnetwork.repository.AccessRoleRepository;
import com.myproject.socialnetwork.service.AccessRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccessRoleServiceImpl implements AccessRoleService {

    private final AccessRoleRepository accessRoleRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public AccessRole findById(Long id) {
        return accessRoleRepository.findById(id).orElseThrow(() -> {
            log.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public AccessRole findByName(Role name) {
        return accessRoleRepository.findByName(name).orElseThrow(() -> {
            log.error("No element with such login - {}.", name);
            throw new NoSuchElementException(name.name());
        });
    }

}
