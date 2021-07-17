package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserOfCommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserOfCommunityService {

    private final UserOfCommunityRepository userOfCommunityRepository;

    private final UserRepository userRepository;

    private final CommunityRepository communityRepository;

    public UserOfCommunity add(UserOfCommunity userOfCommunity) {
        if(userRepository.findById(userOfCommunity.getUser().getId()).isEmpty() ||
        communityRepository.findById(userOfCommunity.getCommunity().getId()).isEmpty())
            throw new NoSuchElementException();
        userOfCommunity.setId(null);
        return userOfCommunityRepository.save(userOfCommunity);
    }

    public List<UserOfCommunity> findAll() {
        return userOfCommunityRepository.findAll();
    }

    public UserOfCommunity findById(Long id) {
        return userOfCommunityRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public UserOfCommunity update(Long id, UserOfCommunity userOfCommunity) {

        if(userRepository.findById(userOfCommunity.getUser().getId()).isEmpty() ||
                communityRepository.findById(userOfCommunity.getCommunity().getId()).isEmpty())
            throw new NoSuchElementException();

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
