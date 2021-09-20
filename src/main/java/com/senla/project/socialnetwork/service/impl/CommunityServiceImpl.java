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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public Community add(Community community) {
        community.setCreator(userService.findByLogin(Utils.getLogin()));
        community.setDateCreated(LocalDate.now());

        return communityRepository.save(community);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Community> findAll() {
        return communityRepository.findAllByCreatorLoginOrderByDateCreated(Utils.getLogin());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public Community findById(Long communityId) {
        //TODO method for "if" conditions
        return communityRepository.findById(communityId).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", communityId);
            throw new NoSuchElementException(communityId);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public Community findByName(String name) {
        //TODO method for search by name especially for creator
        return communityRepository.findByNameAndCreatorLogin(name, Utils.getLogin()).orElseThrow(() -> {
            LOGGER.error("No element with such name - {} or you not creator of that community.", name);
            throw new NoSuchElementException(name);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Community> searchBySubstringOfName(String name) {
        //TODO endpoint
        return communityRepository.findAllByNameContaining(name);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public Community update(Long id, Community community) {
        findByName(findById(id).getName());

        if (userService.findById(community.getCreator().getId()).getRole().getName().equals(Role.USER)) {
            LOGGER.error("User don`t have enough permissions to be creator.");
            throw new TryingModifyNotYourDataException("User don`t have enough permissions to be creator.");
        }

        Community updatedCommunity = findById(id);

        if (Objects.nonNull(community.getCreator())) {
            updatedCommunity.setCreator(userService.findById(community.getCreator().getId()));
        }
        if (Objects.nonNull(community.getName())) {
            updatedCommunity.setName(community.getName());
        }
        if (Objects.nonNull(community.getDescription())) {
            updatedCommunity.setDescription(community.getDescription());
        }

        return communityRepository.save(updatedCommunity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void delete(Long id) {
        findByName(findById(id).getName());

        communityRepository.deleteById(id);
    }

}
