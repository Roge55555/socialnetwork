package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.service.BlocklistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BlocklistController {
    private final BlocklistService blocklistService;

    public BlocklistController(BlocklistService blocklistService) {
        this.blocklistService = blocklistService;
    }

    @GetMapping("/blocklists")
    public List<Blocklist> getAllEmployees(){
        return blocklistService.findAll();
    }

    @GetMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Blocklist getById(@PathVariable("id") Long id) {
        return blocklistService.findById(id);
    }

    @PostMapping("/blocklists")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody Blocklist blocklist) {
        blocklistService.add(blocklist);
    }

    @PutMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody Blocklist accessRole) {
        blocklistService.update(id, accessRole);
    }

    @DeleteMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        blocklistService.delete(id);
    }
}
