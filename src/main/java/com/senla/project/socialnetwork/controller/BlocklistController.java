package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.service.BlocklistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class BlocklistController {
    private final BlocklistService blocklistService;

    @GetMapping("/blocklists")
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Blocklist> getAllEmployees(){
        return blocklistService.findAll();
    }

    @GetMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('communities:permission')")
    public Blocklist getById(@PathVariable("id") Long id) {
        return blocklistService.findById(id);
    }

    @PostMapping("/blocklists")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void addContact(@RequestBody Blocklist blocklist) {
        blocklistService.add(blocklist);
    }

    @PutMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody Blocklist accessRole) {
        blocklistService.update(id, accessRole);
    }

    @DeleteMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        blocklistService.delete(id);
    }
}
