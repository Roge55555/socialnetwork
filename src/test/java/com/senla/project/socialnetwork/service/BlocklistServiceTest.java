package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BlocklistServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    CommunityService communityService;

    @Autowired
    BlocklistService blocklistService;


    @Test
    void successAdd() {
        Blocklist blocklist = new Blocklist();
        blocklist.setCommunity(communityService.findById(2L));
        blocklist.setWhoBaned(userService.findById(2L));
        blocklist.setWhomBaned(userService.findById(3L));
        blocklist.setBlockDate(LocalDate.of(2021, 7, 14));
        blocklist.setBlockCause("Test block");


        final Blocklist block = blocklistService.add(blocklist);
        Assertions.assertEquals(blocklistService.findById(2L), block);
    }

    @Test
    void addTryingToUseNotExistingWhoBaned() {
        Blocklist blockWho = blocklistService.findById(1L);
        User userWho = userService.findById(3L);
        userWho.setId(5L);
        blockWho.setWhoBaned(userWho);
        assertThatThrownBy(() -> blocklistService.add(blockWho))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void addTryingToUseNotExistingWhomBaned() {
        Blocklist blockWhom = blocklistService.findById(1L);
        User userWhom = userService.findById(3L);
        userWhom.setId(5L);
        blockWhom.setWhomBaned(userWhom);
        assertThatThrownBy(() -> blocklistService.add(blockWhom))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void addTryingToUseNotExistingCommunity() {
        Blocklist blockCommunity = blocklistService.findById(1L);
        Community community = communityService.findById(1L);
        community.setId(4L);
        blockCommunity.setCommunity(community);
        assertThatThrownBy(() -> blocklistService.add(blockCommunity))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void addTryingToBanedYourself() {
        Blocklist blockYourself = blocklistService.findById(1L);
        blockYourself.setWhomBaned(userService.findById(2L));
        blockYourself.setWhoBaned(userService.findById(2L));
        assertThatThrownBy(() -> blocklistService.add(blockYourself))
                .isInstanceOf(TryingRequestToYourselfException.class);
    }

    @Test
    void findAll() {
        final List<Blocklist> blocklists = blocklistService.findAll();
        Assertions.assertEquals(1, blocklists.size());
    }

    @Test
    void findByIdSuccess() {
        final List<Blocklist> blocklists = blocklistService.findAll();
        Assertions.assertEquals(blocklists.get(0), blocklistService.findById(1L));
    }

    @Test
    void findByIdException() {
        assertThatThrownBy(() -> blocklistService.findById(13L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateSuccess() {
        Blocklist blocklist = blocklistService.findById(1L);
        blocklist.setWhoBaned(userService.findById(2L));
        blocklist.setWhomBaned(userService.findById(1L));
        blocklist.setCommunity(communityService.findById(2L));
        blocklistService.update(1L, blocklist);
        Assertions.assertEquals(blocklist, blocklistService.findById(1L));

    }

    @Test
    void updateNoSuchElement() {
        Blocklist blocklist = blocklistService.findById(1L);
        blocklist.setWhoBaned(userService.findById(2L));
        blocklist.setWhomBaned(userService.findById(1L));
        blocklist.setCommunity(communityService.findById(2L));
        assertThatThrownBy(() -> blocklistService.update(6L, blocklist))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingWhoBaned() {
        Blocklist blockWho = blocklistService.findById(1L);
        User userWho = userService.findById(3L);
        userWho.setId(5L);
        blockWho.setWhoBaned(userWho);
        assertThatThrownBy(() -> blocklistService.update(1L, blockWho))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingWhomBaned() {
        Blocklist blockWhom = blocklistService.findById(1L);
        User userWhom = userService.findById(3L);
        userWhom.setId(5L);
        blockWhom.setWhomBaned(userWhom);
        assertThatThrownBy(() -> blocklistService.update(1L, blockWhom))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToUseNotExistingCommunity() {
        Blocklist blockCommunity = blocklistService.findById(1L);
        Community community = communityService.findById(1L);
        community.setId(4L);
        blockCommunity.setCommunity(community);
        assertThatThrownBy(() -> blocklistService.update(1L, blockCommunity))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateTryingToBanedYourself() {
        Blocklist blockYourself = blocklistService.findById(1L);
        blockYourself.setWhomBaned(userService.findById(2L));
        blockYourself.setWhoBaned(userService.findById(2L));
        assertThatThrownBy(() -> blocklistService.update(1L, blockYourself))
                .isInstanceOf(TryingRequestToYourselfException.class);
    }

    @Test
    void deleteSuccess() {
        blocklistService.delete(1L);
        assertThatThrownBy(() -> blocklistService.findById(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteNoSuchId() {
        assertThatThrownBy(() -> blocklistService.delete(11L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
