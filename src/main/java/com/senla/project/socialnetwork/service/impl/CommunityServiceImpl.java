package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.Role;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.service.CommunityService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityServiceImpl.class);

    @Override
    public Community add(Community community) {
        community.setCreator(userService.findByLogin(Utils.getLogin()));
        community.setDateCreated(LocalDate.now());

        final Community save = communityRepository.save(community);
        LOGGER.info("Community added with id = {}.", save.getId());
        return save;
    }

    @Override
    public List<Community> findAll() {
        LOGGER.info("Trying to show all communities you create.");

        if (communityRepository.findAll().isEmpty()) {
            LOGGER.warn("Community`s list is empty!");
        } else {
            LOGGER.info("Community(es) found.");
        }

        return communityRepository.findAllByCreatorOrderByDateCreated(userService.findByLogin(Utils.getLogin()));
    }

    @Override
    public Community findById(Long id) {
        LOGGER.info("Trying to find community by id");
        final Community community = communityRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {} or you not part of that community.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("Community found using id - {}", id);
        return community;
    }

    @Override
    public Community findByName(String name) {
        LOGGER.info("Trying to find community by name");
        final Community community = communityRepository.findByNameAndCreator(name,
                userService.findByLogin(Utils.getLogin())).orElseThrow(() -> {
            LOGGER.error("No element with such name - {} or you not part of that community.", name);
            throw new NoSuchElementException(name);
        });
        LOGGER.info("Community found using name {}", name);
        return community;
    }

    @Override
    public Community update(String name, Community community) {
        LOGGER.info("Trying to update community - {}.", name);
        if (communityRepository.findByNameAndCreator(name,
                userService.findByLogin(Utils.getLogin())).isEmpty()) {
            LOGGER.error("Community doesn`t exist or not creator trying to update community.");
            throw new TryingModifyNotYourDataException("Community doesn`t exist or not creator trying to update community.");
        }
        if (community.getCreator().getRole().getName().equals(Role.USER)) {
            LOGGER.error("Creator don`t have enough permissions.");
            throw new TryingModifyNotYourDataException("Creator don`t have enough permissions.");
        }

        return communityRepository.findByNameAndCreator(name,
                userService.findByLogin(Utils.getLogin())).map(com -> {
            com.setCreator(community.getCreator());
            com.setName(community.getName());
            com.setDescription(community.getDescription());
            final Community save = communityRepository.save(com);
            LOGGER.info("Community {} updated.", community.getName());
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element {}.", name);
                    throw new NoSuchElementException(name);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete community with id - {}.", id);
        if (communityRepository.findById(id).isEmpty()) {
            LOGGER.error("No community with id - {}.", id);
            throw new NoSuchElementException(id);
        } else if (!communityRepository.findById(id).get().getCreator().equals(
                userService.findByLogin(Utils.getLogin()))) {
            LOGGER.error("Community doesn`t exist or not creator trying to update community.");
            throw new TryingModifyNotYourDataException("Only creator can delete community.");
        }
        communityRepository.deleteById(id);
        LOGGER.info("Community with id - {} was deleted.", id);
    }

}
