package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.ProfileCommentRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfileCommentService {

    private final ProfileCommentRepository profileCommentRepository;

    private final UserRepository userRepository;

    public ProfileComment add(ProfileComment profileComment) {
        if(userRepository.findById(profileComment.getUser().getId()).isEmpty() ||
                userRepository.findById(profileComment.getProfileOwner().getId()).isEmpty())
            throw new NoSuchElementException();
        return profileCommentRepository.save(profileComment);
    }

    public List<ProfileComment> findAll() {
        return profileCommentRepository.findAll();
    }

    public ProfileComment findById(Long id) {
        return profileCommentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public ProfileComment update(Long id, ProfileComment profileComment) {

        if(userRepository.findById(profileComment.getUser().getId()).isEmpty() ||
                userRepository.findById(profileComment.getProfileOwner().getId()).isEmpty())
            throw new NoSuchElementException();

        return profileCommentRepository.findById(id).map(pc -> {
            pc.setProfileOwner(profileComment.getProfileOwner());
            pc.setUser(profileComment.getUser());
            pc.setDate(profileComment.getDate());
            pc.setCommentTxt(profileComment.getCommentTxt());
            return profileCommentRepository.save(pc);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (profileCommentRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        profileCommentRepository.deleteById(id);
    }
}
