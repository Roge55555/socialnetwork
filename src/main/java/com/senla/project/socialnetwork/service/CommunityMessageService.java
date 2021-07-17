package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityMessageRepository;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityMessageService {

    private final CommunityMessageRepository communityMessageRepository;

    private final UserRepository userRepository;

    private final CommunityRepository communityRepository;

    public CommunityMessage add(CommunityMessage communityMessage) {
        if (userRepository.findById(communityMessage.getCreator().getId()).isEmpty() ||
                communityRepository.findById(communityMessage.getCommunity().getId()).isEmpty())
            throw new NoSuchElementException();
        communityMessage.setId(null);
        return communityMessageRepository.save(communityMessage);
    }

    public List<CommunityMessage> findAll() {
        return communityMessageRepository.findAll();
    }

    public CommunityMessage findById(Long id) {
        return communityMessageRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public CommunityMessage update(Long id, CommunityMessage communityMessage) {

        if (userRepository.findById(communityMessage.getCreator().getId()).isEmpty() ||
                communityRepository.findById(communityMessage.getCommunity().getId()).isEmpty())
            throw new NoSuchElementException();

        return communityMessageRepository.findById(id).map(comm -> {
            comm.setCreator(communityMessage.getCreator());
            comm.setCommunity(communityMessage.getCommunity());
            comm.setDate(communityMessage.getDate());
            comm.setTxt(communityMessage.getTxt());
            return communityMessageRepository.save(comm);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (communityMessageRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        communityMessageRepository.deleteById(id);
    }
}
