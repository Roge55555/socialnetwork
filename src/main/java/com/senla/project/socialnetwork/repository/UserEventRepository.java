package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    List<UserEvent> findByNameContainingAndUserIdOrderByDate(@NotBlank @Size(min = 8) String name, Long userId);

    Optional<UserEvent> findByIdAndUserId(Long id, Long userId);

}
