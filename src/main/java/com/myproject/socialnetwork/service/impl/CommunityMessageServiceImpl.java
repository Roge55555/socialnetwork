package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.entity.CommunityMessage_;
import com.myproject.socialnetwork.exeptions.NoAccessForBlockedUserException;
import com.myproject.socialnetwork.service.CommunityService;
import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.CommunityMessage;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.model.filter.CommunityMessageFilterRequest;
import com.myproject.socialnetwork.repository.CommunityMessageRepository;
import com.myproject.socialnetwork.repository.specification.CommunityMessageSpecification;
import com.myproject.socialnetwork.service.BlocklistService;
import com.myproject.socialnetwork.service.CommunityMessageService;
import com.myproject.socialnetwork.service.UserOfCommunityService;
import com.myproject.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommunityMessageServiceImpl implements CommunityMessageService {

    private final CommunityMessageRepository communityMessageRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private final UserOfCommunityService userOfCommunityService;

    private final BlocklistService blocklistService;

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public CommunityMessage add(CommunityMessage communityMessage) {

        if (Objects.nonNull(blocklistService.findByCommunityIdAndWhomBanedId(communityMessage.getCommunity().getId(), userService.findByLogin(Utils.getLogin()).getId()))) {
            log.error("User - {} is blocked in community - {}", Utils.getLogin(), communityMessage.getCommunity().getId());
            throw new NoAccessForBlockedUserException("You are blocked in this community!");
        }

        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityService.findById(communityMessage.getCommunity().getId()).getName(), Utils.getLogin()).isEmpty() && //TODO you are in community
                !userService.findByLogin(Utils.getLogin()).equals(communityService.findById(communityMessage.getCommunity().getId()).getCreator())) { //TODO you are creator of community
            log.error("Not member of community can not add messages.");
            throw new TryingModifyNotYourDataException("Not member of community can not add messages!");
        }

        return communityMessageRepository.save(
                CommunityMessage.builder()
                        .creator(userService.findByLogin(Utils.getLogin()))
                        .community(communityService.findById(communityMessage.getCommunity().getId()))
                        .date(LocalDateTime.now().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS))
                        .txt(communityMessage.getTxt())
                        .build());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<CommunityMessage> findAll(CommunityMessageFilterRequest request) {

        if (Objects.nonNull(blocklistService.findByCommunityIdAndWhomBanedId(request.getCommunityId(), userService.findByLogin(Utils.getLogin()).getId()))) {
            log.error("User - {} is blocked in community - {}", Utils.getLogin(), request.getCommunityId());
            throw new NoAccessForBlockedUserException("You are blocked in this community!");
        }

        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityService.findById(request.getCommunityId()).getName(), Utils.getLogin()).isEmpty() && //TODO you are in community
                !Utils.getLogin().equals(communityService.findById(request.getCommunityId()).getCreator().getLogin())) { //TODO you are creator of community
            log.error("Not member of community can`t see their messages.");
            throw new TryingModifyNotYourDataException("Not member of community can`t see their messages!");
        }

        return communityMessageRepository.findAll(CommunityMessageSpecification.getSpecification(request), Sort.by(CommunityMessage_.DATE));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public CommunityMessage findById(Long id) {

        final CommunityMessage communityMessage = communityMessageRepository.findById(id).orElseThrow(() -> {
            log.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });

        if (Objects.nonNull(blocklistService.findByCommunityIdAndWhomBanedId(communityMessage.getCommunity().getId(), userService.findByLogin(Utils.getLogin()).getId()))) {
            log.error("User - {} is blocked in community - {}", Utils.getLogin(), communityMessage.getCommunity().getId());
            throw new NoAccessForBlockedUserException("You are blocked in this community!");
        }

        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityMessage.getCommunity().getName(), Utils.getLogin()).isEmpty() && //TODO you are in community
                !Utils.getLogin().equals(communityMessage.getCommunity().getCreator().getLogin())) { //TODO you are creator of community
            log.error("Not member of community can`t see their messages.");
            throw new TryingModifyNotYourDataException("Not member of community can`t see their messages!");
        }

        return communityMessage;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public CommunityMessage update(Long id, String txt) {

        CommunityMessage savedCommunityMessage = findById(id);

        if (!userService.findByLogin(Utils.getLogin()).equals(savedCommunityMessage.getCreator())) {
            log.error("Trying update not his message");
            throw new TryingModifyNotYourDataException("You can update only yourself messages!");
        }

        savedCommunityMessage.setTxt(txt);

        return communityMessageRepository.save(savedCommunityMessage);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void delete(Long id) {

        findById(id);

        if (!(userService.findByLogin(Utils.getLogin()).equals(findById(id).getCreator()) && //TODO you creator of message +
                Objects.nonNull(userOfCommunityService.findByCommunityNameAndUserLogin(findById(id).getCommunity().getName(), Utils.getLogin()))) && //TODO + you not deleted from community
                !userService.findByLogin(Utils.getLogin()).equals(communityService.findById(findById(id).getCommunity().getId()).getCreator())) { //TODO you creator of community
            log.error("Only admin and creator can delete message in community.");
            throw new TryingModifyNotYourDataException("Only admin and creator can delete message in community!");
        }

        communityMessageRepository.deleteById(id);
    }

}
