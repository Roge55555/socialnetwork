package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Page<User> findByLoginContainingOrFirstNameContainingOrLastNameContaining(@NotBlank String login, @NotBlank String firstName, @NotBlank String lastName, Pageable pageable);

}
