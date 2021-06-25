package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.repository.CommunityMessageRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityMessageService {

    private CommunityMessageRepository communityMessageRepository;

    public CommunityMessageService(CommunityMessageRepository communityMessageRepository) {
        this.communityMessageRepository = communityMessageRepository;
    }

    public void add(CommunityMessage communityMessage){
        communityMessageRepository.save(communityMessage);
    }

    public List<CommunityMessage> findAll(){
        return communityMessageRepository.findAll();
    }

    @SneakyThrows
    public CommunityMessage findById(Long id){
        return communityMessageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public CommunityMessage update(Long id, CommunityMessage communityMessage) {

        return communityMessageRepository.findById(id).map(comm -> {
            comm.setCreator_id(communityMessage.getCreator_id());
            comm.setCommunity_id(communityMessage.getCommunity_id());
            comm.setDate(communityMessage.getDate());
            comm.setTxt(communityMessage.getTxt());
            return communityMessageRepository.save(comm);
        })
                .orElseThrow(() ->
                        new IllegalArgumentException()//new NoSuchIdException(id, "update")
                );
    }

    public void delete(Long id) {
        communityMessageRepository.deleteById(id);
    }
}
