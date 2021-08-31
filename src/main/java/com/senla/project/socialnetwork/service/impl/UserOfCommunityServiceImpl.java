package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.repository.UserOfCommunityRepository;
import com.senla.project.socialnetwork.service.CommunityService;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOfCommunityServiceImpl implements UserOfCommunityService {

    private final UserOfCommunityRepository userOfCommunityRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOfCommunityServiceImpl.class);

    @Override
    public UserOfCommunity add(Long communityId, Long userId) {
        if (!Utils.getLogin().equals(communityService.findById(communityId).getCreator().getLogin())) {
            LOGGER.error("Only creator can add users.");
            throw new TryingModifyNotYourDataException("Only creator can add users.");
        }

        return userOfCommunityRepository.save(
                UserOfCommunity.builder()
                        .community(communityService.findById(communityId))
                        .user(userService.findById(userId))
                        .dateEntered(LocalDate.now())
                        .build());
    }

    @Override
    public List<UserOfCommunity> findAllCommunitiesOfUser(String userLogin) {
        if (!Utils.getLogin().equals(userLogin)) {
            LOGGER.error("Trying to check subscriptions of other person - {}", userLogin);
            throw new TryingModifyNotYourDataException("You can check only yourself subscriptions.");
        }

        return userOfCommunityRepository.findByUserLoginOrderByCommunity(userLogin);
    }

    @Override
    public List<UserOfCommunity> findAllUsersOfCommunity(String communityName) {
        if (findByCommunityNameAndUserLogin(communityName, Utils.getLogin()) != null) {
            LOGGER.error("Trying to check subscribers of community - {}, by not member.", communityName);
            throw new TryingModifyNotYourDataException("You can check only yourself subscriptions.");
        }

        return userOfCommunityRepository.findByCommunityNameOrderByUser(communityName);
    }

    @Override
    public UserOfCommunity findByCommunityNameAndUserLogin(String communityName, String userLogin) {
        return userOfCommunityRepository.findByCommunityNameAndUserLogin(communityName, userLogin).orElseThrow(() -> {
            LOGGER.error("No subscription: user - {} to community - {}.", userLogin, communityName);
            throw new NoSuchElementException(userLogin + " / " + communityName);
        });
    }

    @Override
    public void delete(Long communityId, Long userId) {
        if (!Utils.getLogin().equals(communityService.findById(communityId).getCreator().getLogin()) ||
                !Utils.getLogin().equals(userService.findById(userId).getLogin())) { //TODO findByCommunityNameAndUserLogin()
            LOGGER.error("Only creator can delete user from community {}.", Utils.getLogin());
            throw new TryingModifyNotYourDataException("Only creator can delete user from community.");
        }

        if (userOfCommunityRepository.findByCommunityNameAndUserLogin(communityService.findById(communityId).getName(), userService.findById(userId).getLogin()).isEmpty()) {
            LOGGER.error("No subscriber - {} in community - {}.", userId, communityId);
            throw new NoSuchElementException(userId + " / " + communityId);
        }

        userOfCommunityRepository.deleteByCommunityIdAndUserId(userId, communityId);
    }

}
