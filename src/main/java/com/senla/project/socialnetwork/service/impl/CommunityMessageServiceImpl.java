package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.entity.CommunityMessage_;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.filter.CommunityMessageFilterRequest;
import com.senla.project.socialnetwork.repository.CommunityMessageRepository;
import com.senla.project.socialnetwork.repository.specification.CommunityMessageSpecification;
import com.senla.project.socialnetwork.service.CommunityMessageService;
import com.senla.project.socialnetwork.service.CommunityService;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommunityMessageServiceImpl implements CommunityMessageService {

    private final CommunityMessageRepository communityMessageRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private final UserOfCommunityService userOfCommunityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityMessageServiceImpl.class);

    @Override
    public CommunityMessage add(CommunityMessage communityMessage) {
        if (Objects.isNull(userOfCommunityService.findByCommunityNameAndUserLogin(communityService.findById(communityMessage.getCommunity().getId()).getName(), Utils.getLogin())) &&
                !userService.findByLogin(Utils.getLogin()).equals(communityService.findByName(communityMessage.getCommunity().getName()).getCreator())) {
            LOGGER.error("Not member of community can not add messages.");
            throw new TryingModifyNotYourDataException("Not member of community can not add messages!");
        }

        return communityMessageRepository.save(
                CommunityMessage.builder()
                        .creator(userService.findByLogin(Utils.getLogin()))
                        .community(communityService.findById(communityMessage.getCommunity().getId()))
                        .date(LocalDateTime.now())
                        .txt(communityMessage.getTxt())
                        .build());
    }

    @Override
    public List<CommunityMessage> findAll(CommunityMessageFilterRequest request) {
        if (Objects.isNull(userOfCommunityService.findByCommunityNameAndUserLogin(communityService.findById(request.getCommunityId()).getName(), Utils.getLogin())) &&
                !Utils.getLogin().equals(communityService.findById(request.getCommunityId()).getCreator().getLogin())) {
            LOGGER.error("Not member of community can`t see their messages.");
            throw new TryingModifyNotYourDataException("Not member of community can`t see their messages!");
        }
        return communityMessageRepository.findAll(CommunityMessageSpecification.getSpecification(request), Sort.by(CommunityMessage_.DATE));
    }

    @Override
    public CommunityMessage findById(Long id) {
        if (Objects.isNull(userOfCommunityService.findByCommunityNameAndUserLogin(communityMessageRepository.findById(id).get().getCommunity().getName(), Utils.getLogin())) &&
                !Utils.getLogin().equals(communityMessageRepository.findById(id).get().getCommunity().getCreator().getLogin())) {
            LOGGER.error("Not member of community can`t see their messages.");
            throw new TryingModifyNotYourDataException("Not member of community can`t see their messages!");
        }

        return communityMessageRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

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

    @Override
    public void delete(Long id) {
        if (!(userService.findByLogin(Utils.getLogin()).equals(findById(id).getCreator()) &&
                Objects.nonNull(userOfCommunityService.findByCommunityIdAndUserId(findById(id).getCommunity().getId(), userService.findByLogin(Utils.getLogin()).getId()))) &&
                !userService.findByLogin(Utils.getLogin()).equals(communityService.findById(findById(id).getCommunity().getId()).getCreator())) {
            LOGGER.error("Only admin and creator can delete message in community.");
            throw new TryingModifyNotYourDataException("Only admin and creator can delete message in community!");
        }

        if (communityMessageRepository.findById(id).isEmpty()) {
            LOGGER.error("No community message with id - {}.", id);
            throw new NoSuchElementException(id);
        }

        communityMessageRepository.deleteById(id);
    }

}
