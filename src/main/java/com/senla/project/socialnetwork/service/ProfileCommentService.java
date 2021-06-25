package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.ProfileComment;
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
            pc.setProfile_owner_id(profileComment.getProfile_owner_id());
            pc.setUser_id(profileComment.getUser_id());
            pc.setComment_date(profileComment.getComment_date());
            pc.setComment_txt(profileComment.getComment_txt());
            return profileCommentRepository.save(pc);
        })
                .orElseThrow(() ->
                        new IllegalArgumentException()//new NoSuchIdException(id, "update")
                );
    }

    public void delete(Long id) {
        profileCommentRepository.deleteById(id);
    }
}
