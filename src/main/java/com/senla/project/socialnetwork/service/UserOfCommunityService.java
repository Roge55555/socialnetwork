package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserOfCommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserOfCommunityService {

    private final UserOfCommunityRepository userOfCommunityRepository;

    private final UserRepository userRepository;

    private final CommunityRepository communityRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOfCommunityService.class);

    public UserOfCommunity add(UserOfCommunity userOfCommunity) {
        LOGGER.info("Trying to add user of community.");

        if (userRepository.findById(userOfCommunity.getUser().getId()).isEmpty() ||
                communityRepository.findById(userOfCommunity.getCommunity().getId()).isEmpty()) {
            LOGGER.error("Subscriber/Community do(es)n`t exist");
            throw new NoSuchElementException();
        }
        userOfCommunity.setId(null);
        UserOfCommunity save = userOfCommunityRepository.save(userOfCommunity);
        LOGGER.info("User added to community.");
        return save;
    }

    public List<UserOfCommunity> findAll() {
        LOGGER.info("Trying to show all subscribers.");
        if (userOfCommunityRepository.findAll().isEmpty()) {
            LOGGER.warn("Subscriber`s list is empty!");
        } else {
            LOGGER.info("Subscriber(s) found.");
        }
        return userOfCommunityRepository.findAll();
    }

    public UserOfCommunity findById(Long id) {
        LOGGER.info("Trying to find subscriber by id");
        final UserOfCommunity userOfCommunity = userOfCommunityRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Subscriber found using id {}", userOfCommunity.getId());
        return userOfCommunity;
    }

    public UserOfCommunity update(Long id, UserOfCommunity userOfCommunity) {
        LOGGER.info("Trying to update subscriber with id - {}.", id);
        if (userRepository.findById(userOfCommunity.getUser().getId()).isEmpty() ||
                communityRepository.findById(userOfCommunity.getCommunity().getId()).isEmpty()) {
            LOGGER.error("Subscriber/Community do(es)n`t exist");
            throw new NoSuchElementException(id);
        }


        return userOfCommunityRepository.findById(id).map(uoc -> {
            uoc.setCommunity(userOfCommunity.getCommunity());
            uoc.setUser(userOfCommunity.getUser());
            uoc.setDateEntered(userOfCommunity.getDateEntered());
            UserOfCommunity save = userOfCommunityRepository.save(uoc);
            LOGGER.info("Subscriber with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    public void delete(Long id) {
        LOGGER.info("Trying to delete subscriber with id - {}.", id);
        if (userOfCommunityRepository.findById(id).isEmpty()) {
            LOGGER.error("No subscriber with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        userOfCommunityRepository.deleteById(id);
        LOGGER.info("Subscriber with id - {} was deleted.", id);
    }
}
