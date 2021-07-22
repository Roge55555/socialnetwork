package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.senla.project.socialnetwork.repository.BlocklistRepository;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlocklistService {

    private final BlocklistRepository blocklistRepository;

    private final UserRepository userRepository;

    private final CommunityRepository communityRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BlocklistService.class);

    public Blocklist add(Blocklist blocklist) {
        LOGGER.info("Trying to add blocklist.");

        if (userRepository.findById(blocklist.getWhoBaned().getId()).isEmpty() ||
                userRepository.findById(blocklist.getWhomBaned().getId()).isEmpty() ||
                communityRepository.findById(blocklist.getCommunity().getId()).isEmpty()) {
            LOGGER.error("Who baned/Whom baned/Community do(es)n`t exist");
            throw new NoSuchElementException();
        } else if (blocklist.getWhoBaned().getId().equals(blocklist.getWhomBaned().getId())) {
            LOGGER.error("User {} trying ban himself", blocklist.getWhoBaned());
            throw new TryingRequestToYourselfException();
        }
        blocklist.setId(null);
        final Blocklist save = blocklistRepository.save(blocklist);
        LOGGER.info("Blocklist added.");
        return save;
    }

    public List<Blocklist> findAll() {
        LOGGER.info("Trying to show all blocklists.");
        if (blocklistRepository.findAll().isEmpty()) {
            LOGGER.warn("Blocklist`s list is empty!");
        } else {
            LOGGER.info("Blocklist(s) found.");
        }
        return blocklistRepository.findAll();
    }

    public Blocklist findById(Long id) {
        LOGGER.info("Trying to find blocklist by id");
        final Blocklist blocklist = blocklistRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Blocklist found using id {}", blocklist.getId());
        return blocklist;
    }

    public Blocklist update(Long id, Blocklist blocklist) {
        LOGGER.info("Trying to update blocklist with id - {}.", id);
        if (userRepository.findById(blocklist.getWhoBaned().getId()).isEmpty() ||
                userRepository.findById(blocklist.getWhomBaned().getId()).isEmpty() ||
                communityRepository.findById(blocklist.getCommunity().getId()).isEmpty()) {
            LOGGER.error("Who baned/Whom baned/Community do(es)n`t exist");
            throw new NoSuchElementException(id);
        } else if (blocklist.getWhoBaned().getId().equals(blocklist.getWhomBaned().getId())) {
            LOGGER.error("User {} trying ban himself", blocklist.getWhoBaned());
            throw new TryingRequestToYourselfException();
        }

        return blocklistRepository.findById(id).map(bl -> {
            bl.setCommunity(blocklist.getCommunity());
            bl.setWhoBaned(blocklist.getWhoBaned());
            bl.setWhomBaned(blocklist.getWhomBaned());
            bl.setBlockDate(blocklist.getBlockDate());
            bl.setBlockCause(blocklist.getBlockCause());
            final Blocklist save = blocklistRepository.save(bl);
            LOGGER.info("Blocklist with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    public void delete(Long id) {
        LOGGER.info("Trying to delete blocklist with id - {}.", id);
        if (blocklistRepository.findById(id).isEmpty()) {
            LOGGER.error("No blocklist with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        blocklistRepository.deleteById(id);
        LOGGER.info("Blocklist with id - {} was deleted.", id);
    }
}
