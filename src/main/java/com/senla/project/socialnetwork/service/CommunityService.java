package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    public Community add(Community community) {
        if(userRepository.findById(community.getCreator().getId()).isEmpty())
            throw new NoSuchElementException();
        return communityRepository.save(community);
    }

    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    public Community findById(Long id) {
        return communityRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Community update(Long id, Community community) {

        if(userRepository.findById(community.getCreator().getId()).isEmpty())
            throw new NoSuchElementException();


        return communityRepository.findById(id).map(com -> {
            com.setCreator(community.getCreator());
            com.setName(community.getName());
            com.setDescription(community.getDescription());
            com.setDateCreated(community.getDateCreated());
            return communityRepository.save(com);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (communityRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        communityRepository.deleteById(id);
    }
}
