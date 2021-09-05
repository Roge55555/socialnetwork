package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.Blocklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BlocklistRepository extends JpaRepository<Blocklist, Long> {

    List<Blocklist> findBlocklistByWhoBanedIdOrderByBlockDate(Long whoBanedId);

    List<Blocklist> findBlocklistByWhomBanedIdOrderByBlockDate(Long whomBanedId);

    List<Blocklist> findBlocklistByCommunityIdOrderByBlockDate(Long communityId);

    List<Blocklist> findBlocklistByBlockDateBetweenOrderByBlockDate(LocalDate from, LocalDate to);

}
