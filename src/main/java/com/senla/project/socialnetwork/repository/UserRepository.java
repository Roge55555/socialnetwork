package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(@NotBlank @Size(min = 4) String login);

    Optional<User> findByEmail(@NotBlank @Email String email);

    Optional<User> findByPhone(@NotBlank @Size(min = 7, max = 13) String phone);

    Page<User> findByLoginContainingOrFirstNameContainingOrLastNameContaining(@NotBlank @Size(min = 4) String login, @NotBlank String firstName, @NotBlank String lastName, Pageable pageable);

}
