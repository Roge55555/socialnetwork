package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.ProfileCommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfileCommentService {

    private ProfileCommentRepository profileCommentRepository;

    public void add(ProfileComment profileComment) {
        profileCommentRepository.save(profileComment);
    }

    public List<ProfileComment> findAll() {
        if (profileCommentRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return profileCommentRepository.findAll();
    }

    public ProfileComment findById(Long id) {
        return profileCommentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public ProfileComment update(Long id, ProfileComment profileComment) {

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
