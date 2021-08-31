package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.ProfileComment;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileCommentServiceImpl implements ProfileCommentService {

    private final ProfileCommentRepository profileCommentRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCommentServiceImpl.class);

    @Override
    public ProfileComment add(Long ownerId, String txt) {
        LOGGER.info("Trying to add comment.");

        if (userService.findById(ownerId) == null) {
            LOGGER.error("Profile owner doesn`t exist");
            throw new NoSuchElementException();
        }

        ProfileComment profileComment = ProfileComment.builder()
                .profileOwner(userService.findById(ownerId))
                .user(userService.findByLogin(Utils.getLogin()))
                .date(LocalDateTime.now())
                .commentTxt(txt)
                .build();
        ProfileComment save = profileCommentRepository.save(profileComment);
        LOGGER.info("Comment added.");
        return save;
    }

    @Override
    public List<ProfileComment> findAll(ProfileCommentFilterRequest request) {
        return profileCommentRepository.findAll(ProfileCommentSpecification.getSpecification(request), Sort.by("date"));
    }

    @Override
    public ProfileComment findById(Long id) {
        LOGGER.info("Trying to find comment by id");
        final ProfileComment comment = profileCommentRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("Comment found using id {}", comment.getId());
        return comment;
    }

    @Override
    public ProfileComment update(Long id, String txt) {
        LOGGER.info("Trying to update comment with id - {}.", id);
        if (!findById(id).getUser().getLogin().equals(Utils.getLogin())) {
            LOGGER.error("Trying update not his message");
            throw new TryingModifyNotYourDataException("You can update only yourself messages!");
        }


        return profileCommentRepository.findById(id).map(pc -> {
            pc.setDate(LocalDateTime.now());
            pc.setCommentTxt(txt);
            ProfileComment save = profileCommentRepository.save(pc);
            LOGGER.info("Comment with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    throw new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete comment with id - {}.", id);
        if (!findById(id).getProfileOwner().getLogin().equals(Utils.getLogin()) ||
                !findById(id).getUser().getLogin().equals(Utils.getLogin())) {
            LOGGER.error("Trying delete not his message or message in not his/her profile.");
            throw new TryingModifyNotYourDataException("You can delete only yourself messages or your profile messages!");
        }
        profileCommentRepository.deleteById(id);
        LOGGER.info("Comment with id - {} was deleted.", id);
    }

}
