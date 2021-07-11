package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.BlocklistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlocklistService {

    private BlocklistRepository blocklistRepository;

    public void add(Blocklist blocklist) {
        blocklistRepository.save(blocklist);
    }

    public List<Blocklist> findAll() {
        if (blocklistRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return blocklistRepository.findAll();
    }

    public Blocklist findById(Long id) {
        return blocklistRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Blocklist update(Long id, Blocklist blocklist) {

        return blocklistRepository.findById(id).map(bl -> {
            bl.setCommunity(blocklist.getCommunity());
            bl.setWhoBaned(blocklist.getWhoBaned());
            bl.setWhomBaned(blocklist.getWhomBaned());
            bl.setBlockDate(blocklist.getBlockDate());
            bl.setBlockCause(blocklist.getBlockCause());
            return blocklistRepository.save(bl);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (blocklistRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        blocklistRepository.deleteById(id);
    }
}
