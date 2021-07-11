package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.UserEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserEventService {

    private UserEventRepository userEventRepository;

    public void add(UserEvent userEvent) {
        userEventRepository.save(userEvent);
    }

    public List<UserEvent> findAll() {
        if (userEventRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return userEventRepository.findAll();
    }

    public UserEvent findById(Long id) {
        return userEventRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public UserEvent update(Long id, UserEvent userEvent) {

        return userEventRepository.findById(id).map(ue -> {
            ue.setUser(userEvent.getUser());
            ue.setName(userEvent.getName());
            ue.setDescription(userEvent.getDescription());
            ue.setDate(userEvent.getDate());
            return userEventRepository.save(ue);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (userEventRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        userEventRepository.deleteById(id);
    }
}
