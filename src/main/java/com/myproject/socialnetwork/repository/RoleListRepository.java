package com.myproject.socialnetwork.repository;

import com.myproject.socialnetwork.entity.RoleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleListRepository extends JpaRepository<RoleList, Long> {

    List<RoleList> findByNameContaining(String name);

}
