package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityService.class);

    public Community add(Community community) {
        if (userRepository.findById(community.getCreator().getId()).isEmpty()) {
            LOGGER.error("Creator doesn`t exist");
            throw new NoSuchElementException(community.getCreator().getId());
        }
        community.setId(null);
        final Community save = communityRepository.save(community);
        LOGGER.info("Community added.");
        return save;
    }

    public List<Community> findAll() {
        LOGGER.info("Trying to show all communities.");
        if (communityRepository.findAll().isEmpty()) {
            LOGGER.warn("Community`s list is empty!");
        } else {
            LOGGER.info("Community(es) found.");
        }
        return communityRepository.findAll();
    }

    public Page<Community> findAll(Pageable pageable) {
        LOGGER.info("Trying to show all communities in pages.");
        if (communityRepository.findAll().isEmpty())
            LOGGER.warn("Community`s list is empty!");
        else
            LOGGER.info("Community(es) found.");
        return communityRepository.findAll(pageable);
    }

    public Community findById(Long id) {
        LOGGER.info("Trying to find community by id");
        final Community community = communityRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Community found using id {}", community.getId());
        return community;
    }

    public Community update(Long id, Community community) {
        LOGGER.info("Trying to update community with id - {}.", id);
        if (userRepository.findById(community.getCreator().getId()).isEmpty()) {
            LOGGER.error("Creator doesn`t exist");
            throw new NoSuchElementException(id);
        }


        return communityRepository.findById(id).map(com -> {
            com.setCreator(community.getCreator());
            com.setName(community.getName());
            com.setDescription(community.getDescription());
            com.setDateCreated(community.getDateCreated());
            final Community save = communityRepository.save(com);
            LOGGER.info("Community with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    public void delete(Long id) {
        LOGGER.info("Trying to delete community with id - {}.", id);
        if (communityRepository.findById(id).isEmpty()) {
            LOGGER.error("No community with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        communityRepository.deleteById(id);
        LOGGER.info("Community with id - {} was deleted.", id);
    }
}
