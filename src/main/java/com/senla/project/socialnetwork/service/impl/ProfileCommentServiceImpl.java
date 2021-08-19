package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.ProfileCommentRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.ProfileCommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileCommentServiceImpl implements ProfileCommentService {

    private final ProfileCommentRepository profileCommentRepository;

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCommentServiceImpl.class);

    @Override
    public ProfileComment add(ProfileComment profileComment) {
        LOGGER.info("Trying to add comment.");

        if (userRepository.findById(profileComment.getUser().getId()).isEmpty() ||
                userRepository.findById(profileComment.getProfileOwner().getId()).isEmpty()) {
            LOGGER.error("Profile owner/Sender do(es)n`t exist");
            throw new NoSuchElementException();
        }
        profileComment.setId(null);
        ProfileComment save = profileCommentRepository.save(profileComment);
        LOGGER.info("Comment added.");
        return save;
    }

    @Override
    public List<ProfileComment> findAll() {
        LOGGER.info("Trying to show all comments.");
        if (profileCommentRepository.findAll().isEmpty()) {
            LOGGER.warn("Comment`s list is empty!");
        } else {
            LOGGER.info("Comment(s) found.");
        }
        return profileCommentRepository.findAll();
    }

    @Override
    public ProfileComment findById(Long id) {
        LOGGER.info("Trying to find comment by id");
        final ProfileComment comment = profileCommentRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Comment found using id {}", comment.getId());
        return comment;
    }

    @Override
    public ProfileComment update(Long id, ProfileComment profileComment) {
        LOGGER.info("Trying to update comment with id - {}.", id);
        if (userRepository.findById(profileComment.getUser().getId()).isEmpty() ||
                userRepository.findById(profileComment.getProfileOwner().getId()).isEmpty()) {
            LOGGER.error("Profile owner/Sender do(es)n`t exist");
            throw new NoSuchElementException();
        }


        return profileCommentRepository.findById(id).map(pc -> {
            pc.setProfileOwner(profileComment.getProfileOwner());
            pc.setUser(profileComment.getUser());
            pc.setDate(profileComment.getDate());
            pc.setCommentTxt(profileComment.getCommentTxt());
            ProfileComment save = profileCommentRepository.save(pc);
            LOGGER.info("Comment with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete comment with id - {}.", id);
        if (profileCommentRepository.findById(id).isEmpty()) {
            LOGGER.error("No comment with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        profileCommentRepository.deleteById(id);
        LOGGER.info("Comment with id - {} was deleted.", id);
    }

}
