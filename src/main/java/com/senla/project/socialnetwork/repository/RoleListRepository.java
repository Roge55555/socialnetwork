package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.RoleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleListRepository extends JpaRepository<RoleList, Long> {
}
