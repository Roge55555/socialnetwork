package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.Blocklist;

import java.time.LocalDate;
import java.util.List;

public interface BlocklistService {

    Blocklist add(Blocklist blocklist);

    List<Blocklist> findAllBannsOf(Long whomBanedId);

    List<Blocklist> findAllBannedBy(Long whoBanedId);

    List<Blocklist> findAllBannedIn(Long communityId);

    List<Blocklist> findAllBannsBetween(LocalDate from, LocalDate to);

    Blocklist findById(Long id);

    Blocklist findByCommunityIdAndWhomBanedId(Long communityId, Long whomBanedId);

    void delete(Long id);

}
