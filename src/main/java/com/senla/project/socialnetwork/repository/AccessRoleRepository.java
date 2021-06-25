package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRoleRepository extends JpaRepository<AccessRole, Long> {
}
