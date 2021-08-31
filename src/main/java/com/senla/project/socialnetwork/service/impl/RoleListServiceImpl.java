package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.RoleListRepository;
import com.senla.project.socialnetwork.service.RoleListService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleListServiceImpl implements RoleListService {

    private final RoleListRepository roleListRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleListServiceImpl.class);

    @Override
    public RoleList add(RoleList roleList) {
        LOGGER.info("Trying to add role.");
        roleList.setId(null);
        RoleList save = roleListRepository.save(roleList);
        LOGGER.info("Role added.");
        return save;
    }

    @Override
    public List<RoleList> findAllWith(String name) {
        LOGGER.info("Trying to show all roles.");
        if (roleListRepository.findByNameContaining(name).isEmpty()) {
            LOGGER.warn("Role`s list is empty!");
        } else {
            LOGGER.info("Role(s) found.");
        }
        return roleListRepository.findByNameContaining(name);
    }

    @Override
    public RoleList findById(Long id) {
        LOGGER.info("Trying to find role by id");
        final RoleList roleList = roleListRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("Role found using id {}", roleList.getId());
        return roleList;
    }

    @Override
    public RoleList update(Long id, RoleList roleList) {
        LOGGER.info("Trying to update role with id - {}.", id);

        return roleListRepository.findById(id).map(rl -> {
            rl.setName(roleList.getName());
            RoleList save = roleListRepository.save(rl);
            LOGGER.info("Role with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    throw new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete role with id - {}.", id);
        if (roleListRepository.findById(id).isEmpty()) {
            LOGGER.error("No role with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        roleListRepository.deleteById(id);
        LOGGER.info("Role with id - {} was deleted.", id);
    }

}
