package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.entity.CommunityMessage_;
import com.senla.project.socialnetwork.exeptions.NoAccessForBlockedUserException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.filter.CommunityMessageFilterRequest;
import com.senla.project.socialnetwork.repository.CommunityMessageRepository;
import com.senla.project.socialnetwork.repository.specification.CommunityMessageSpecification;
import com.senla.project.socialnetwork.service.BlocklistService;
import com.senla.project.socialnetwork.service.CommunityMessageService;
import com.senla.project.socialnetwork.service.CommunityService;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CommunityMessageServiceImpl implements CommunityMessageService {

    private final CommunityMessageRepository communityMessageRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private final UserOfCommunityService userOfCommunityService;

    private final BlocklistService blocklistService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityMessageServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public CommunityMessage add(CommunityMessage communityMessage) {

        if (Objects.nonNull(blocklistService.findByCommunityIdAndWhomBanedId(communityMessage.getCommunity().getId(), userService.findByLogin(Utils.getLogin()).getId()))) {
            LOGGER.error("User - {} is blocked in community - {}", Utils.getLogin(), communityMessage.getCommunity().getId());
            throw new NoAccessForBlockedUserException("You are blocked in this community!");
        }

        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityService.findById(communityMessage.getCommunity().getId()).getName(), Utils.getLogin()).isEmpty() && //TODO you are in community
                !userService.findByLogin(Utils.getLogin()).equals(communityService.findById(communityMessage.getCommunity().getId()).getCreator())) { //TODO you are creator of community
            LOGGER.error("Not member of community can not add messages.");
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
            LOGGER.error("User - {} is blocked in community - {}", Utils.getLogin(), request.getCommunityId());
            throw new NoAccessForBlockedUserException("You are blocked in this community!");
        }

        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityService.findById(request.getCommunityId()).getName(), Utils.getLogin()).isEmpty() && //TODO you are in community
                !Utils.getLogin().equals(communityService.findById(request.getCommunityId()).getCreator().getLogin())) { //TODO you are creator of community
            LOGGER.error("Not member of community can`t see their messages.");
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
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });

        if (Objects.nonNull(blocklistService.findByCommunityIdAndWhomBanedId(communityMessage.getCommunity().getId(), userService.findByLogin(Utils.getLogin()).getId()))) {
            LOGGER.error("User - {} is blocked in community - {}", Utils.getLogin(), communityMessage.getCommunity().getId());
            throw new NoAccessForBlockedUserException("You are blocked in this community!");
        }

        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityMessage.getCommunity().getName(), Utils.getLogin()).isEmpty() && //TODO you are in community
                !Utils.getLogin().equals(communityMessage.getCommunity().getCreator().getLogin())) { //TODO you are creator of community
            LOGGER.error("Not member of community can`t see their messages.");
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
            LOGGER.error("Trying update not his message");
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
            LOGGER.error("Only admin and creator can delete message in community.");
            throw new TryingModifyNotYourDataException("Only admin and creator can delete message in community!");
        }

        communityMessageRepository.deleteById(id);
    }

}
