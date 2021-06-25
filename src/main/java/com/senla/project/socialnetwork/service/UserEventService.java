package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.repository.UserEventRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEventService {

    private UserEventRepository userEventRepository;

    public UserEventService(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }

    public void add(UserEvent userEvent){
        userEventRepository.save(userEvent);
    }

    public List<UserEvent> findAll(){
        return userEventRepository.findAll();
    }

    @SneakyThrows
    public UserEvent findById(Long id){
        return userEventRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public UserEvent update(Long id, UserEvent userEvent) {

        return userEventRepository.findById(id).map(ue -> {
            ue.setUser_id(userEvent.getUser_id());
            ue.setName(userEvent.getName());
            ue.setDescription(userEvent.getDescription());
            ue.setDate(userEvent.getDate());
            return userEventRepository.save(ue);
        })
                .orElseThrow(() ->
                        new IllegalArgumentException()//new NoSuchIdException(id, "update")
                );
    }

    public void delete(Long id) {
        userEventRepository.deleteById(id);
    }
}
