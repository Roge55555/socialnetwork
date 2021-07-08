package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.exeptions.NoSuchIdException;
import com.senla.project.socialnetwork.repository.BlocklistRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlocklistService {

    private BlocklistRepository blocklistRepository;

    public BlocklistService(BlocklistRepository blocklistRepository) {
        this.blocklistRepository = blocklistRepository;
    }

    public void add(Blocklist blocklist){
        blocklistRepository.save(blocklist);
    }

    public List<Blocklist> findAll(){
        return blocklistRepository.findAll();
    }

    @SneakyThrows
    public Blocklist findById(Long id){
        return blocklistRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public Blocklist update(Long id, Blocklist blocklist) {

        return blocklistRepository.findById(id).map(bl -> {
            bl.setCommunity(blocklist.getCommunity());
            bl.setWhoBaned(blocklist.getWhoBaned());
            bl.setWhomBaned(blocklist.getWhomBaned());
            bl.setBlockDate(blocklist.getBlockDate());
            bl.setBlockCause(blocklist.getBlockCause());
            return blocklistRepository.save(bl);
        })
                .orElseThrow(() ->
                        new NoSuchIdException(id, "blocked user")
                );
    }

    public void delete(Long id) {
        blocklistRepository.deleteById(id);
    }
}
