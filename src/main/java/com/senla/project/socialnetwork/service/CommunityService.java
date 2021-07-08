package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.exeptions.NoSuchIdException;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private CommunityRepository communityRepository;

    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public void add(Community community){
        communityRepository.save(community);
    }

    public List<Community> findAll(){
        return communityRepository.findAll();
    }

    @SneakyThrows
    public Community findById(Long id){
        return communityRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public Community update(Long id, Community community) {

        return communityRepository.findById(id).map(com -> {
            com.setCreator(community.getCreator());
            com.setName(community.getName());
            com.setDescription(community.getDescription());
            com.setDateCreated(community.getDateCreated());
            return communityRepository.save(com);
        })
                .orElseThrow(() ->
                        new NoSuchIdException(id, "community")
                );
    }

    public void delete(Long id) {
        communityRepository.deleteById(id);
    }
}
