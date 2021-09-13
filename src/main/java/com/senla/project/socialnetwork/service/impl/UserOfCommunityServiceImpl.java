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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserOfCommunityServiceImpl implements UserOfCommunityService {

    private final UserOfCommunityRepository userOfCommunityRepository;

    private final UserService userService;

    private final CommunityService communityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOfCommunityServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public UserOfCommunity add(UserOfCommunity userOfCommunity) {
        if (!Utils.getLogin().equals(communityService.findById(userOfCommunity.getCommunity().getId()).getCreator().getLogin())) {
            LOGGER.error("Only creator can add users.");
            throw new TryingModifyNotYourDataException("Only creator can add users.");
        }
        return userOfCommunityRepository.save(UserOfCommunity.builder().community(communityService.findById(userOfCommunity.getCommunity().getId())).user(userService.findById(userOfCommunity.getUser().getId())).dateEntered(LocalDate.now()).build());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<UserOfCommunity> findAllCommunitiesOfUser() {
        return userOfCommunityRepository.findByUserIdOrderByCommunity(userService.findByLogin(Utils.getLogin()).getId());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<UserOfCommunity> findAllUsersOfCommunity(Long communityId) {
        if (Objects.isNull(findByCommunityNameAndUserLogin(communityService.findById(communityId).getName(), Utils.getLogin()))) {
            LOGGER.error("Trying to check subscribers of community - {}, by not member.", communityId);
            throw new TryingModifyNotYourDataException("You can check only yourself subscriptions.");
        }
        return userOfCommunityRepository.findByCommunityIdOrderByUser(communityId);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public UserOfCommunity findByCommunityNameAndUserLogin(String communityName, String userLogin) {
        return userOfCommunityRepository.findByCommunityNameAndUserLogin(communityName, userLogin).orElseThrow(() -> {
            LOGGER.error("No subscription: user - {} to community - {}.", userLogin, communityName);
            throw new NoSuchElementException(userLogin + " / " + communityName);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public UserOfCommunity findByCommunityIdAndUserId(Long communityId, Long userId) {
        if(!Utils.getLogin().equals(communityService.findById(communityId).getCreator().getLogin())) {
            LOGGER.error("Trying to check subscription of other community - {}", Utils.getLogin());
            throw new TryingModifyNotYourDataException("You can check only yourself subscription.");
        }

        return userOfCommunityRepository.findByCommunityIdAndUserId(communityId, userId).orElseThrow(() -> {
            LOGGER.error("No subscription: userId - {} to communityId - {}.", userId, communityId);
            throw new NoSuchElementException(userId + " / " + communityId);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void delete(Long communityId, Long userId) {
        if (Objects.isNull(communityService.findById(communityId).getCreator().getLogin()) &&
                (Objects.isNull(findByCommunityNameAndUserLogin(communityService.findById(communityId).getName(), Utils.getLogin())) &&
                        Utils.getLogin().equals(userService.findById(userId).getLogin()))) {
            LOGGER.error("Only creator/subscribers can delete user/yourself from community {}.", Utils.getLogin());
            throw new TryingModifyNotYourDataException("Only creator/subscribers can delete user/yourself from community.");
        }

        userOfCommunityRepository.deleteByCommunityIdAndUserId(communityId, userId);
    }

}
