package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.Blocklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocklistRepository extends JpaRepository<Blocklist, Long> {
}
