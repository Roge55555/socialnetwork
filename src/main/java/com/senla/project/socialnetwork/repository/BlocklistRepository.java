package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BlocklistRepository extends JpaRepository<Blocklist, Long> {

    List<Blocklist> findBlocklistByWhomBanedOrderByBlockDate(User whomBaned);

    List<Blocklist> findBlocklistByWhoBanedOrderByBlockDate(User whomBaned);

    List<Blocklist> findBlocklistByCommunityOrderByBlockDate(Community community);

    List<Blocklist> findBlocklistByBlockDateBetweenOrderByBlockDate(LocalDate from, LocalDate to);

}
