package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.CommunityMessage;
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

@Service
@RequiredArgsConstructor
public class CommunityMessageServiceImpl implements CommunityMessageService {

    private final CommunityMessageRepository communityMessageRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private final UserOfCommunityService userOfCommunityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityMessageServiceImpl.class);

    @Override
    public CommunityMessage add(String communityName, String txt) {
        LOGGER.info("Trying to add community message.");

        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityName, Utils.getLogin()) == null &&
        !communityService.findByName(communityName).getCreator().equals(userService.findByLogin(Utils.getLogin()))) {
            LOGGER.error("Not member of community can not add messages.");
            throw new TryingModifyNotYourDataException("Not member of community can not add messages!");
        }
        if (communityService.findByName(communityName) == null) {
            LOGGER.error("Community do(es)n`t exist");
            throw new NoSuchElementException();
        }

        CommunityMessage communityMessage = CommunityMessage.builder()
                .creator(userService.findByLogin(Utils.getLogin()))
                .community(communityService.findByName(communityName))
                .date(LocalDateTime.now())
                .txt(txt)
                .build();
        final CommunityMessage save = communityMessageRepository.save(communityMessage);
        LOGGER.info("Community message added.");
        return save;
    }

//    @Override
//    public List<CommunityMessage> findCommunityMessagesByCommunityName(String communityName) {
//        LOGGER.info("Trying to show all community messages.");
//        if (communityMessageRepository.findCommunityMessagesByCommunityNameOrderByDate(communityName).isEmpty()) {
//            LOGGER.warn("Community message`s list is empty!");
//        } else {
//            LOGGER.info("Community message(s) found.");
//        }
//        return communityMessageRepository.findCommunityMessagesByCommunityNameOrderByDate(communityName);
//    }
//
//    @Override
//    public List<CommunityMessage> findCommunityMessagesByCommunityNameAndCreatorLogin(String communityName, String userLogin) {
//        LOGGER.info("Trying to show all community messages.");
//        if (communityMessageRepository.findCommunityMessagesByCommunityNameAndCreatorLoginOrderByDate(communityName, userLogin).isEmpty()) {
//            LOGGER.warn("Community message`s list is empty!");
//        } else {
//            LOGGER.info("Community message(s) found.");
//        }
//        return communityMessageRepository.findCommunityMessagesByCommunityNameAndCreatorLoginOrderByDate(communityName, userLogin);
//    }
//
//    @Override
//    public List<CommunityMessage> findCommunityMessagesByDateBetween(LocalDateTime from, LocalDateTime to) {
//        LOGGER.info("Trying to show all community messages.");
//        if (communityMessageRepository.findCommunityMessagesByDateBetweenOrderByDate(from, to).isEmpty()) {
//            LOGGER.warn("Community message`s list is empty!");
//        } else {
//            LOGGER.info("Community message(s) found.");
//        }
//        return communityMessageRepository.findCommunityMessagesByDateBetweenOrderByDate(from, to);
//    }

    @Override
    public List<CommunityMessage> findAll(CommunityMessageFilterRequest request) {

        return communityMessageRepository.findAll(CommunityMessageSpecification.getSpecification(request), Sort.by("date"));
    }

    @Override
    public CommunityMessage findById(Long id) {
        LOGGER.info("Trying to find community message by id");
        final CommunityMessage communityMessage = communityMessageRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("Community message found using id {}", communityMessage.getId());
        return communityMessage;
    }

    @Override
    public CommunityMessage update(Long id, String txt) {
        LOGGER.info("Trying to update community message with id - {}.", id);
        if (!findById(id).getCreator().equals(userService.findByLogin(Utils.getLogin()))) {
            LOGGER.error("Trying update not his message");
            throw new TryingModifyNotYourDataException("You can update only yourself messages!");
        }


        return communityMessageRepository.findById(id).map(comm -> {
            comm.setDate(LocalDateTime.now());
            comm.setTxt(txt);
            final CommunityMessage save = communityMessageRepository.save(comm);
            LOGGER.info("Community message with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    throw new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete community message with id - {}.", id);
        if (userOfCommunityService.findByCommunityNameAndUserLogin(communityService.findById(id).getName(), Utils.getLogin()) == null &&
                !communityService.findById(id).getCreator().equals(userService.findByLogin(Utils.getLogin()))) {
            LOGGER.error("Not member of community can not delete messages.");
            throw new TryingModifyNotYourDataException("Not member of community can not delete messages!");
        }
        if (communityMessageRepository.findById(id).isEmpty()) {
            LOGGER.error("No community message with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        communityMessageRepository.deleteById(id);
        LOGGER.info("Community message with id - {} was deleted.", id);
    }

}
