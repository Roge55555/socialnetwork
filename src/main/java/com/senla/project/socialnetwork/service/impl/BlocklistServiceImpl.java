package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.senla.project.socialnetwork.repository.BlocklistRepository;
import com.senla.project.socialnetwork.service.BlocklistService;
import com.senla.project.socialnetwork.service.CommunityService;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
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

@Service
@RequiredArgsConstructor
public class BlocklistServiceImpl implements BlocklistService {

    private final BlocklistRepository blocklistRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private final UserOfCommunityService userOfCommunityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BlocklistServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class, TryingRequestToYourselfException.class})
    @Override
    public Blocklist add(Blocklist blocklist) {
        if (!userService.findByLogin(Utils.getLogin()).equals(communityService.findById(blocklist.getCommunity().getId()).getCreator())) {
            LOGGER.error("Trying give ban in not his community - {}." + Utils.getLogin());
            throw new TryingModifyNotYourDataException("Only creator of community can give ban!");
        }

        if (userService.findByLogin(Utils.getLogin()).getId().equals(blocklist.getWhomBaned().getId())) {
            LOGGER.error("User {} trying ban himself", blocklist.getWhoBaned());
            throw new TryingRequestToYourselfException();
        }

        userOfCommunityService.findByCommunityIdAndUserId(blocklist.getCommunity().getId(), blocklist.getWhomBaned().getId());

        return blocklistRepository.save(
                Blocklist.builder()
                        .community(communityService.findById(blocklist.getCommunity().getId()))
                        .whoBaned(userService.findByLogin(Utils.getLogin()))
                        .whomBaned(userService.findById(blocklist.getWhomBaned().getId()))
                        .blockDate(LocalDate.now())
                        .blockCause(blocklist.getBlockCause())
                        .build());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Blocklist> findAllBannsOf(Long whomBanedId) {
        return blocklistRepository.findBlocklistByWhomBanedIdOrderByBlockDate(whomBanedId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Blocklist> findAllBannedBy(Long whoBanedId) {
        return blocklistRepository.findBlocklistByWhoBanedIdOrderByBlockDate(whoBanedId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Blocklist> findAllBannedIn(Long communityId) {
        return blocklistRepository.findBlocklistByCommunityIdOrderByBlockDate(communityId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Blocklist> findAllBannsBetween(LocalDate from, LocalDate to) {
        return blocklistRepository.findBlocklistByBlockDateBetweenOrderByBlockDate(from, to);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public Blocklist findById(Long id) {
        return blocklistRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public Blocklist findByCommunityIdAndWhomBanedId(Long communityId, Long whomBanedId) {
        //TODO only for inside check
        //TODO no endpoint
        return blocklistRepository.findBlocklistByCommunityIdAndWhomBanedId(communityId, whomBanedId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void delete(Long id) {
        if (!userService.findByLogin(Utils.getLogin()).equals(findById(id).getCommunity().getCreator())) {
            LOGGER.error("Trying remove ban in not his community - {}.", Utils.getLogin());
            throw new TryingModifyNotYourDataException("Only admin of community can remove ban!");
        }

        blocklistRepository.deleteById(id);
    }

}
