package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.LoginEmailPhoneAlreadyTakenException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.NotOldPasswordException;
import com.senla.project.socialnetwork.model.ChangePassword;
import com.senla.project.socialnetwork.model.Role;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AccessRoleService accessRoleService;

    public Long add(User user) {
        user.setRole(accessRoleService.findByName(Role.USER));
        user.setIsActive(true);
        user.setIsBlocked(false);
        user.setRegistrationDate(LocalDate.now());

        if (userRepository.findByLogin(user.getLogin()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent() ||
                userRepository.findByPhone(user.getPhone()).isPresent())
            throw new LoginEmailPhoneAlreadyTakenException();

        return userRepository.save(user).getId();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new NoSuchElementException(login));
    }

    public User update(Long id, User user) throws NoSuchElementException {

        if ((userRepository.findByLogin(user.getLogin()).isPresent() && !userRepository.findByLogin(user.getLogin()).get().getId().equals(id)) ||
                (userRepository.findByEmail(user.getEmail()).isPresent() && !userRepository.findByEmail(user.getEmail()).get().getId().equals(id)) ||
                (userRepository.findByPhone(user.getPhone()).isPresent() && !userRepository.findByPhone(user.getPhone()).get().getId().equals(id)))
            throw new LoginEmailPhoneAlreadyTakenException();

        return userRepository.save(userRepository.findById(id).map(usr -> //TODO проверять существует ли логин
                User.builder()
                        .id(id)
                        .login(user.getLogin())
                        .password(usr.getPassword())
                        .dateBirth(user.getDateBirth())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .role(usr.getRole())
                        .isActive(usr.getIsActive())
                        .isBlocked(usr.getIsBlocked())
                        .registrationDate(usr.getRegistrationDate())
                        .phone(user.getPhone())
                        .website(user.getWebsite())
                        .aboutYourself(user.getAboutYourself())
                        .jobTitle(user.getJobTitle())
                        .workPhone(user.getWorkPhone())
                        .build())
                .orElseThrow(NoSuchElementException::new));
    }

    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        userRepository.deleteById(id);
    }

    public User changePassword(Long id, ChangePassword password) throws NoSuchElementException, NotOldPasswordException {
        if (!userRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        } else if (new BCryptPasswordEncoder(12).matches(password.getOldPassword(), userRepository.findById(id).get().getPassword())) {
            return userRepository.findById(id).map(usr -> {
                usr.setPassword(new BCryptPasswordEncoder(12).encode(password.getNewPassword()));

                return userRepository.save(usr);
            }).orElseThrow();
        }
        throw new NotOldPasswordException();
    }

}
