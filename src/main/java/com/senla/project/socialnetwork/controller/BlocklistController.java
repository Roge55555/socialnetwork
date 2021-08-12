package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.service.BlocklistService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class BlocklistController {

    private final BlocklistService blocklistService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BlocklistController.class);

    @GetMapping("/blocklists")
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Blocklist> getAllBlocklists() {
        LOGGER.debug("Entering findAll blocklists endpoint");
        return blocklistService.findAll();
    }

    @GetMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('communities:permission')")
    public Blocklist getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById blocklist endpoint");
        return blocklistService.findById(id);
    }

    @PostMapping("/blocklists")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public Blocklist addBlocklist(@RequestBody Blocklist blocklist) {
        LOGGER.debug("Entering addBlocklist endpoint");
        return blocklistService.add(blocklist);
    }

    @PutMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateBlocklist(@PathVariable("id") Long id, @RequestBody Blocklist accessRole) {
        LOGGER.debug("Entering updateBlocklist endpoint");
        blocklistService.update(id, accessRole);
    }

    @DeleteMapping("/blocklists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteBlocklist(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteBlocklist endpoint");
        blocklistService.delete(id);
    }
}
