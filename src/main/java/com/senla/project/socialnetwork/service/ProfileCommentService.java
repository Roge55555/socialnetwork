package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.exeptions.NoSuchIdException;
import com.senla.project.socialnetwork.repository.ProfileCommentRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileCommentService {

    private ProfileCommentRepository profileCommentRepository;

    public ProfileCommentService(ProfileCommentRepository profileCommentRepository) {
        this.profileCommentRepository = profileCommentRepository;
    }

    public void add(ProfileComment profileComment){
        profileCommentRepository.save(profileComment);
    }

    public List<ProfileComment> findAll(){
        return profileCommentRepository.findAll();
    }

    @SneakyThrows
    public ProfileComment findById(Long id){
        return profileCommentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public ProfileComment update(Long id, ProfileComment profileComment) {

        return profileCommentRepository.findById(id).map(pc -> {
            pc.setProfileOwner(profileComment.getProfileOwner());
            pc.setUser(profileComment.getUser());
            pc.setDate(profileComment.getDate());
            pc.setCommentTxt(profileComment.getCommentTxt());
            return profileCommentRepository.save(pc);
        })
                .orElseThrow(() ->
                        new NoSuchIdException(id, "profile comment")
                );
    }

    public void delete(Long id) {
        profileCommentRepository.deleteById(id);
    }
}
