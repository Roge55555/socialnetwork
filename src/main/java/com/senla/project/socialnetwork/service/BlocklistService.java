package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Blocklist;

import java.util.List;

public interface BlocklistService {

    Blocklist add(Blocklist blocklist);

    List<Blocklist> findAll();

    Blocklist findById(Long id);

    Blocklist update(Long id, Blocklist blocklist);

    void delete(Long id);

}
