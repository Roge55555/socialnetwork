package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.ProfileComment;
import com.myproject.socialnetwork.entity.ProfileComment_;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.repository.ProfileCommentRepository;
import com.myproject.socialnetwork.service.UserService;
import com.myproject.socialnetwork.model.filter.ProfileCommentFilterRequest;
import com.myproject.socialnetwork.repository.specification.ProfileCommentSpecification;
import com.myproject.socialnetwork.service.ProfileCommentService;
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

@Service
@RequiredArgsConstructor
public class ProfileCommentServiceImpl implements ProfileCommentService {

    private final ProfileCommentRepository profileCommentRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCommentServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public ProfileComment add(ProfileComment profileComment) {
        return profileCommentRepository.save(
                ProfileComment.builder()
                        .profileOwner(userService.findById(profileComment.getProfileOwner().getId()))
                        .user(userService.findByLogin(Utils.getLogin()))
                        .date(LocalDateTime.now().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS))
                        .commentTxt(profileComment.getCommentTxt())
                        .build());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<ProfileComment> findAll(ProfileCommentFilterRequest request) {
        return profileCommentRepository.findAll(ProfileCommentSpecification.getSpecification(request), Sort.by(ProfileComment_.DATE));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public ProfileComment findById(Long id) {
        return profileCommentRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public ProfileComment update(Long id, String txt) {
        if (!Utils.getLogin().equals(findById(id).getUser().getLogin())) {
            LOGGER.error("Trying update not your message");
            throw new TryingModifyNotYourDataException("You can update only yourself messages!");
        }

        ProfileComment profileComment = findById(id);
        profileComment.setCommentTxt(txt);
        return profileCommentRepository.save(profileComment);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void delete(Long id) {
        if (!Utils.getLogin().equals(findById(id).getProfileOwner().getLogin()) &&
                !Utils.getLogin().equals(findById(id).getUser().getLogin())) {
            LOGGER.error("Trying delete not your message or message in not your profile.");
            throw new TryingModifyNotYourDataException("You can delete only yourself messages or your profile messages!");
        }

        profileCommentRepository.deleteById(id);
    }

}
