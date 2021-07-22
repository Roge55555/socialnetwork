package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.RoleListRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleListService {

    private final RoleListRepository roleListRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleListService.class);

    public RoleList add(RoleList roleList) {
        LOGGER.info("Trying to add role.");
        roleList.setId(null);
        RoleList save = roleListRepository.save(roleList);
        LOGGER.info("Role added.");
        return save;
    }

    public List<RoleList> findAll() {
        LOGGER.info("Trying to show all roles.");
        if (roleListRepository.findAll().isEmpty()) {
            LOGGER.warn("Role`s list is empty!");
        } else {
            LOGGER.info("Role(s) found.");
        }
        return roleListRepository.findAll();
    }

    public RoleList findById(Long id) {
        LOGGER.info("Trying to find role by id");
        final RoleList roleList = roleListRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Role found using id {}", roleList.getId());
        return roleList;
    }

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
                    return new NoSuchElementException(id);
                });
    }

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
