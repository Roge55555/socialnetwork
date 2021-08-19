package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.model.TimeInterval;
import com.senla.project.socialnetwork.model.dto.BlocklistAddDTO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blocklists")
public class BlocklistController {

    private final BlocklistService blocklistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public Blocklist addBlocklist(@RequestBody BlocklistAddDTO blocklistAddDTO) {
        Blocklist blocklist = Blocklist.builder()
                .community(blocklistAddDTO.getCommunity())
                .whomBaned(blocklistAddDTO.getWhomBaned())
                .blockCause(blocklistAddDTO.getBlockCause())
                .build();
        return blocklistService.add(blocklist);
    }

    @GetMapping("/bansOfUser/{login}")
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Blocklist> getBansOfUser(@PathVariable("login") String login) {
        return blocklistService.findAllBannsOf(login);
    }

    @GetMapping("/adminsBans/{login}")
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Blocklist> getAdminsBans(@PathVariable("login") String login) {
        return blocklistService.findAllBannedBy(login);
    }

    @GetMapping("/bansIn")
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Blocklist> getBansIn(@RequestBody String community) {
        return blocklistService.findAllBannedIn(community);
    }

    @GetMapping("/bansOnInterval")
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Blocklist> getBansOnInterval(@RequestBody TimeInterval<LocalDate> timeInterval) {
        return blocklistService.findAllBannsBetween(timeInterval.getFrom(), timeInterval.getTo());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteBlocklist(@PathVariable("id") Long id) {
        blocklistService.delete(id);
    }

}
