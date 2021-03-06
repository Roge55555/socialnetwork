package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.repository.UserOfCommunityRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOfCommunityService {

    private UserOfCommunityRepository userOfCommunityRepository;

    public UserOfCommunityService(UserOfCommunityRepository userOfCommunityRepository) {
        this.userOfCommunityRepository = userOfCommunityRepository;
    }

    public void add(UserOfCommunity userOfCommunity){
        userOfCommunityRepository.save(userOfCommunity);
    }

    public List<UserOfCommunity> findAll(){
        return userOfCommunityRepository.findAll();
    }

    @SneakyThrows
    public UserOfCommunity findById(Long id){
        return userOfCommunityRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public UserOfCommunity update(Long id, UserOfCommunity userOfCommunity) {

        return userOfCommunityRepository.findById(id).map(uoc -> {
            uoc.setCommunity_id(userOfCommunity.getCommunity_id());
            uoc.setUser_id(userOfCommunity.getUser_id());
            uoc.setDate_entered(userOfCommunity.getDate_entered());
            return userOfCommunityRepository.save(uoc);
        })
                .orElseThrow(() ->
                        new IllegalArgumentException()//new NoSuchIdException(id, "update")
                );
    }

    public void delete(Long id) {
        userOfCommunityRepository.deleteById(id);
    }
}
