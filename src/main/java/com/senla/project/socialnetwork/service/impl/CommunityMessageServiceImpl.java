package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityMessageRepository;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.CommunityMessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityMessageServiceImpl implements CommunityMessageService {

    private final CommunityMessageRepository communityMessageRepository;

    private final UserRepository userRepository;

    private final CommunityRepository communityRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityMessageServiceImpl.class);

    @Override
    public CommunityMessage add(CommunityMessage communityMessage) {
        LOGGER.info("Trying to add community message.");

        if (userRepository.findById(communityMessage.getCreator().getId()).isEmpty() ||
                communityRepository.findById(communityMessage.getCommunity().getId()).isEmpty()) {
            LOGGER.error("Creator/Community do(es)n`t exist");
            throw new NoSuchElementException();
        }
        communityMessage.setId(null);
        final CommunityMessage save = communityMessageRepository.save(communityMessage);
        LOGGER.info("Community message added.");
        return save;
    }

    @Override
    public List<CommunityMessage> findAll() {
        LOGGER.info("Trying to show all community messages.");
        if (communityMessageRepository.findAll().isEmpty()) {
            LOGGER.warn("Community message`s list is empty!");
        } else {
            LOGGER.info("Community message(s) found.");
        }
        return communityMessageRepository.findAll();
    }

    @Override
    public CommunityMessage findById(Long id) {
        LOGGER.info("Trying to find community message by id");
        final CommunityMessage communityMessage = communityMessageRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Community message found using id {}", communityMessage.getId());
        return communityMessage;
    }

    @Override
    public CommunityMessage update(Long id, CommunityMessage communityMessage) {
        LOGGER.info("Trying to update community message with id - {}.", id);
        if (userRepository.findById(communityMessage.getCreator().getId()).isEmpty() ||
                communityRepository.findById(communityMessage.getCommunity().getId()).isEmpty()) {
            LOGGER.error("Creator/Community do(es)n`t exist");
            throw new NoSuchElementException(id);
        }


        return communityMessageRepository.findById(id).map(comm -> {
            comm.setCreator(communityMessage.getCreator());
            comm.setCommunity(communityMessage.getCommunity());
            comm.setDate(communityMessage.getDate());
            comm.setTxt(communityMessage.getTxt());
            final CommunityMessage save = communityMessageRepository.save(comm);
            LOGGER.info("Community message with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete community message with id - {}.", id);
        if (communityMessageRepository.findById(id).isEmpty()) {
            LOGGER.error("No community message with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        communityMessageRepository.deleteById(id);
        LOGGER.info("Community message with id - {} was deleted.", id);
    }
}
