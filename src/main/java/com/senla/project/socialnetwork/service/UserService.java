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
            usr.setDate_birth(user.getDate_birth());
            usr.setFirst_name(user.getFirst_name());
            usr.setLast_name(user.getLast_name());
            usr.setEmail(user.getEmail());
            usr.setPhone(user.getPhone());
            usr.setRole(user.getRole());
            usr.setIs_active(user.getIs_active());
            usr.setIs_blocked(user.getIs_blocked());
            usr.setRegistration_date(user.getRegistration_date());
            usr.setWebsite(user.getWebsite());
            usr.setAbout_yourself(user.getAbout_yourself());
            usr.setJob_title(user.getJob_title());
            usr.setWork_phone(user.getWork_phone());
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
