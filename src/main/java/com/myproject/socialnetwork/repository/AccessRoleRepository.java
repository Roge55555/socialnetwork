package com.myproject.socialnetwork.repository;

import com.myproject.socialnetwork.entity.AccessRole;
import com.myproject.socialnetwork.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessRoleRepository extends JpaRepository<AccessRole, Long> {

    Optional<AccessRole> findByName(Role name);

}
