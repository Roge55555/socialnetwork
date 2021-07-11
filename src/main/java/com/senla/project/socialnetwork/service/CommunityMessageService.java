package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.CommunityMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityMessageService {

    private CommunityMessageRepository communityMessageRepository;

    public void add(CommunityMessage communityMessage) {
        communityMessageRepository.save(communityMessage);
    }

    public List<CommunityMessage> findAll() {
        if (communityMessageRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return communityMessageRepository.findAll();
    }

    public CommunityMessage findById(Long id) {
        return communityMessageRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public CommunityMessage update(Long id, CommunityMessage communityMessage) {

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
