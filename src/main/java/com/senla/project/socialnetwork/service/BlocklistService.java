package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Blocklist;

import java.time.LocalDate;
import java.util.List;

public interface BlocklistService {

    Blocklist add(Blocklist blocklist);

    List<Blocklist> findAllBannsOf(String login);

    List<Blocklist> findAllBannedBy(String login);

    List<Blocklist> findAllBannedIn(String community);

    List<Blocklist> findAllBannsBetween(LocalDate from, LocalDate to);

    void delete(Long id);

}
