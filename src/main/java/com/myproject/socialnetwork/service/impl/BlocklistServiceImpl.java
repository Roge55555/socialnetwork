package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.entity.Blocklist;
import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.myproject.socialnetwork.repository.BlocklistRepository;
import com.myproject.socialnetwork.service.BlocklistService;
import com.myproject.socialnetwork.service.CommunityService;
import com.myproject.socialnetwork.service.UserOfCommunityService;
import com.myproject.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BlocklistServiceImpl implements BlocklistService {

    private final BlocklistRepository blocklistRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private final UserOfCommunityService userOfCommunityService;

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class, TryingRequestToYourselfException.class})
    @Override
    public Blocklist add(Blocklist blocklist) {
        if (!userService.findByLogin(Utils.getLogin()).equals(communityService.findById(blocklist.getCommunity().getId()).getCreator())) {
            log.error("Trying give ban in not his community - {}." + Utils.getLogin());
            throw new TryingModifyNotYourDataException("Only creator of community can give ban!");
        }

        if (userService.findByLogin(Utils.getLogin()).getId().equals(blocklist.getWhomBaned().getId())) {
            log.error("User {} trying ban himself", blocklist.getWhoBaned());
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
            log.error("No element with such id - {}.", id);
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
            log.error("Trying remove ban in not his community - {}.", Utils.getLogin());
            throw new TryingModifyNotYourDataException("Only admin of community can remove ban!");
        }

        blocklistRepository.deleteById(id);
    }

}
