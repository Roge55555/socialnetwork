package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.senla.project.socialnetwork.repository.BlocklistRepository;
import com.senla.project.socialnetwork.service.BlocklistService;
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
public class BlocklistServiceImpl implements BlocklistService {

    private final BlocklistRepository blocklistRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BlocklistServiceImpl.class);

    @Override
    public Blocklist add(Blocklist blocklist) {
        if (!userService.findByLogin(Utils.getLogin()).equals(communityService.findByName(blocklist.getCommunity().getName()).getCreator())) {
            throw new TryingModifyNotYourDataException("Only creator of community can give ban!");
            //LOGGER.error("Trying remove not his ban - {}." + Utils.getLogin(SecurityContextHolder.getContext()));

        }

        blocklist.setWhoBaned(userService.findByLogin(Utils.getLogin()));
        blocklist.setBlockDate(LocalDate.now());

        LOGGER.info("Trying to add blocklist.");
        //TODO
        if (userService.findById(blocklist.getWhoBaned().getId()) == null ||
                userService.findById(blocklist.getWhomBaned().getId()) == null ||
                communityService.findByName(blocklist.getCommunity().getName()) == null) {
            LOGGER.error("Who baned/Whom baned/Community do(es)n`t exist");
            throw new NoSuchElementException();
        } else if (blocklist.getWhoBaned().getId().equals(blocklist.getWhomBaned().getId())) {
            LOGGER.error("User {} trying ban himself", blocklist.getWhoBaned());
            throw new TryingRequestToYourselfException();
        }
        final Blocklist save = blocklistRepository.save(blocklist);
        LOGGER.info("Blocklist added.");
        return save;
    }

    @Override
    public List<Blocklist> findAllBannsOf(String login) { //TODO Logs
        LOGGER.info("Trying to show all blocklists.");
        if (blocklistRepository.findBlocklistByWhoBanedOrderByBlockDate(userService.findByLogin(login)).isEmpty()) {
            LOGGER.warn("Blocklist`s list is empty!");
        } else {
            LOGGER.info("Blocklist(s) found.");
        }
        return blocklistRepository.findBlocklistByWhoBanedOrderByBlockDate(userService.findByLogin(login));
    }

    @Override
    public List<Blocklist> findAllBannedBy(String login) { //TODO Logs
        LOGGER.info("Trying to show all blocklists.");
        if (blocklistRepository.findBlocklistByWhomBanedOrderByBlockDate(userService.findByLogin(login)).isEmpty()) {
            LOGGER.warn("Blocklist`s list is empty!");
        } else {
            LOGGER.info("Blocklist(s) found.");
        }
        return blocklistRepository.findBlocklistByWhomBanedOrderByBlockDate(userService.findByLogin(login));
    }

    @Override
    public List<Blocklist> findAllBannedIn(String community) { //TODO Logs
        LOGGER.info("Trying to show all blocklists.");
        if (blocklistRepository.findBlocklistByCommunityOrderByBlockDate(communityService.findByName(community)).isEmpty()) {
            LOGGER.warn("Blocklist`s list is empty!");
        } else {
            LOGGER.info("Blocklist(s) found.");
        }
        return blocklistRepository.findBlocklistByCommunityOrderByBlockDate(communityService.findByName(community));
    }

    @Override
    public List<Blocklist> findAllBannsBetween(LocalDate from, LocalDate to) { //TODO Logs
        LOGGER.info("Trying to show all blocklists.");
        if (blocklistRepository.findBlocklistByBlockDateBetweenOrderByBlockDate(from, to).isEmpty()) {
            LOGGER.warn("Blocklist`s list is empty!");
        } else {
            LOGGER.info("Blocklist(s) found.");
        }
        return blocklistRepository.findBlocklistByBlockDateBetweenOrderByBlockDate(from, to);
    }

    @Override
    public void delete(Long id) { //TODO Logs
        if (userService.findByLogin(Utils.getLogin()) != blocklistRepository.findById(id).get().getWhoBaned()) {
            throw new TryingModifyNotYourDataException("Only admin who give ban can remove it!");
            //LOGGER.error("Trying remove not his ban - {}." + Utils.getLogin(SecurityContextHolder.getContext()));

        }
        LOGGER.info("Trying to delete blocklist with id - {}.", id);
        if (blocklistRepository.findById(id).isEmpty()) {
            LOGGER.error("No blocklist with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        blocklistRepository.deleteById(id);
        LOGGER.info("Blocklist with id - {} was deleted.", id);
    }

}
