package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.entity.ProfileComment_;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.filter.ProfileCommentFilterRequest;
import com.senla.project.socialnetwork.repository.ProfileCommentRepository;
import com.senla.project.socialnetwork.repository.specification.ProfileCommentSpecification;
import com.senla.project.socialnetwork.service.ProfileCommentService;
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
                .profileOwner(userService.findById(profileComment.getUser().getId()))
                .user(userService.findByLogin(Utils.getLogin()))
                .date(LocalDateTime.now())
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
            LOGGER.error("Trying update not his message");
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
