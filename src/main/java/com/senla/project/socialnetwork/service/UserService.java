package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchIdException;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void add(User user){
        userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @SneakyThrows
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public User update(Long id, User user) {

        return userRepository.findById(id).map(usr -> {
            usr.setLogin(user.getLogin());
            usr.setPassword(user.getPassword());
            usr.setDateBirth(user.getDateBirth());
            usr.setFirstName(user.getFirstName());
            usr.setLastName(user.getLastName());
            usr.setEmail(user.getEmail());
            usr.setPhone(user.getPhone());
            usr.setRole(user.getRole());
            usr.setIsActive(user.getIsActive());
            usr.setIsBlocked(user.getIsBlocked());
            usr.setRegistrationDate(user.getRegistrationDate());
            usr.setWebsite(user.getWebsite());
            usr.setAboutYourself(user.getAboutYourself());
            usr.setJobTitle(user.getJobTitle());
            usr.setWorkPhone(user.getWorkPhone());
            return userRepository.save(usr);
        })
                .orElseThrow(() ->
                        new NoSuchIdException(id, "user")
                );
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
