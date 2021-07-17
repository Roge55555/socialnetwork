package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.UserEventRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserEventService {

    private final UserEventRepository userEventRepository;

    private final UserRepository userRepository;

    public UserEvent add(UserEvent userEvent) {
        if(userRepository.findById(userEvent.getUser().getId()).isEmpty())
            throw new NoSuchElementException();
        userEvent.setId(null);
        return userEventRepository.save(userEvent);
    }

    public List<UserEvent> findAll() {
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
