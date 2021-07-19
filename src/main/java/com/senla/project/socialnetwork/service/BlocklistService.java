package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.senla.project.socialnetwork.repository.BlocklistRepository;
import com.senla.project.socialnetwork.repository.CommunityRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlocklistService {

    private final BlocklistRepository blocklistRepository;

    private final UserRepository userRepository;

    private final CommunityRepository communityRepository;

    public Blocklist add(Blocklist blocklist) {
        if (userRepository.findById(blocklist.getWhoBaned().getId()).isEmpty() ||
                userRepository.findById(blocklist.getWhomBaned().getId()).isEmpty() ||
                communityRepository.findById(blocklist.getCommunity().getId()).isEmpty()) {
            throw new NoSuchElementException();
        } else if (blocklist.getWhoBaned().getId().equals(blocklist.getWhomBaned().getId())) {
            throw new TryingRequestToYourselfException();
        }
        blocklist.setId(null);
        return blocklistRepository.save(blocklist);
    }

    public List<Blocklist> findAll() {
        return blocklistRepository.findAll();
    }

    public Blocklist findById(Long id) {
        return blocklistRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Blocklist update(Long id, Blocklist blocklist) {

        if (userRepository.findById(blocklist.getWhoBaned().getId()).isEmpty() ||
                userRepository.findById(blocklist.getWhomBaned().getId()).isEmpty() ||
                communityRepository.findById(blocklist.getCommunity().getId()).isEmpty()) {
            throw new NoSuchElementException();
        } else if (blocklist.getWhoBaned().getId().equals(blocklist.getWhomBaned().getId())) {
            throw new TryingRequestToYourselfException();
        }

        return blocklistRepository.findById(id).map(bl -> {
            bl.setCommunity(blocklist.getCommunity());
            bl.setWhoBaned(blocklist.getWhoBaned());
            bl.setWhomBaned(blocklist.getWhomBaned());
            bl.setBlockDate(blocklist.getBlockDate());
            bl.setBlockCause(blocklist.getBlockCause());
            return blocklistRepository.save(bl);
        })
                .orElseThrow(() -> new NoSuchElementException(id));
    }

    public void delete(Long id) {
        if (blocklistRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException(id);
        }
        blocklistRepository.deleteById(id);
    }
}
