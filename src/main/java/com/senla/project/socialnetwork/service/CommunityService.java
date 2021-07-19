package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    public Community add(Community community) {
        if(userRepository.findById(community.getCreator().getId()).isEmpty())
            throw new NoSuchElementException(community.getCreator().getId());
        community.setId(null);
        return communityRepository.save(community);
    }

    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    public Page<Community> findAll(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }

    public Community findById(Long id) {
        return communityRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Community update(Long id, Community community) {

        if(userRepository.findById(community.getCreator().getId()).isEmpty())
            throw new NoSuchElementException(id);


        return communityRepository.findById(id).map(com -> {
            com.setCreator(community.getCreator());
            com.setName(community.getName());
            com.setDescription(community.getDescription());
            com.setDateCreated(community.getDateCreated());
            return communityRepository.save(com);
        })
                .orElseThrow(() -> new NoSuchElementException(id));
    }

    public void delete(Long id) {
        if (communityRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException(id);
        }
        communityRepository.deleteById(id);
    }
}
