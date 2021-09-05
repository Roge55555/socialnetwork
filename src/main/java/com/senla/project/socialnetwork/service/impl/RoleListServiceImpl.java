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
    public RoleList add(String roleListName) {
        return roleListRepository.save(
                RoleList.builder()
                        .name(roleListName)
                        .build());
    }

    @Override
    public List<RoleList> findAllWith(String name) {
        return roleListRepository.findByNameContaining(name);
    }

    @Override
    public RoleList findById(Long id) {
        return roleListRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public RoleList update(Long id, String roleListName) {
        RoleList roleList = findById(id);
            roleList.setName(roleList.getName());
            return roleListRepository.save(roleList);
    }

    @Override
    public void delete(Long id) {
        findById(id);

        roleListRepository.deleteById(id);
    }

}
