package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.UserOfCommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserOfCommunityService {

    private UserOfCommunityRepository userOfCommunityRepository;

    public void add(UserOfCommunity userOfCommunity) {
        userOfCommunityRepository.save(userOfCommunity);
    }

    public List<UserOfCommunity> findAll() {
        if (userOfCommunityRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return userOfCommunityRepository.findAll();
    }

    public UserOfCommunity findById(Long id) {
        return userOfCommunityRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public UserOfCommunity update(Long id, UserOfCommunity userOfCommunity) {

        return userOfCommunityRepository.findById(id).map(uoc -> {
            uoc.setCommunity(userOfCommunity.getCommunity());
            uoc.setUser(userOfCommunity.getUser());
            uoc.setDateEntered(userOfCommunity.getDateEntered());
            return userOfCommunityRepository.save(uoc);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (userOfCommunityRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        userOfCommunityRepository.deleteById(id);
    }
}
