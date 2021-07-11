package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessRoleRepository extends JpaRepository<AccessRole, Long> {

    Optional<AccessRole> findByName(Role name);

}
