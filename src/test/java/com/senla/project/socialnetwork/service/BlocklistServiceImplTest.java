package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Blocklist;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.senla.project.socialnetwork.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BlocklistServiceImplTest {

    private final UserService userService;

    private final CommunityService communityService;

    private final BlocklistService blocklistService;

    private final JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void init() {
        String token = jwtTokenProvider.createToken("runsha", "64654564rererer");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void getAuthentication(String login, String password) {
        String token = jwtTokenProvider.createToken(login, password);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Blocklist getBlocklist(Long communityId, Long userId) {
        Blocklist blocklist = new Blocklist();
        blocklist.setCommunity(communityService.findById(communityId));
        blocklist.setWhoBaned(userService.findByLogin(Utils.getLogin()));
        blocklist.setWhomBaned(userService.findById(userId));
        blocklist.setBlockDate(LocalDate.of(2021, 9, 14));
        blocklist.setBlockCause("Test block");
        return blocklist;
    }

    @Test
    @DisplayName("Successful add user to block list of community")
    void successAdd() {
        final Blocklist check = blocklistService.add(getBlocklist(3L, 7L));
        final Blocklist blocklist = blocklistService.add(getBlocklist(3L, 5L));
        assertEquals(blocklistService.findById(check.getId() + 1L), blocklist); //TODO 6 - cause of findByIdSuccess
    }

    @Test
    @DisplayName("Exception when other admin trying to ban user")
    void addBlockByOtherAdminException() {
        getAuthentication("rogE", "55555");
        assertThatThrownBy(() -> blocklistService.add(getBlocklist(3L, 5L)))
                .isInstanceOf(TryingModifyNotYourDataException.class);
    }

    @Test
    @DisplayName("Exception when we trying to ban yourself")
    void addTryingToBanedYourself() {
        assertThatThrownBy(() -> blocklistService.add(getBlocklist(3L, 6L)))
                .isInstanceOf(TryingRequestToYourselfException.class);
    }

    @Test
    @DisplayName("Successful showing all bans of selected user")
    void findAllBannsOf() {
        final List<Blocklist> blocklists = blocklistService.findAllBannsOf(3L);
        assertAll(() -> assertEquals(3L, blocklists.get(0).getWhomBaned().getId()),
                () -> assertEquals(4L, blocklists.get(0).getCommunity().getId()),
                () -> assertEquals(3L, blocklists.get(1).getWhomBaned().getId()),
                () -> assertEquals(3L, blocklists.get(1).getCommunity().getId()),
                () -> assertEquals(2, blocklists.size()));
    }

    @Test
    @DisplayName("Successful showing all users baned by selected admin")
    void findAllBannedBy() {
        final List<Blocklist> blocklists = blocklistService.findAllBannedBy(6L);
        assertAll(() -> assertEquals(6L, blocklists.get(0).getWhoBaned().getId()),
                () -> assertEquals(3L, blocklists.get(0).getWhomBaned().getId()),
                () -> assertEquals(6L, blocklists.get(1).getWhoBaned().getId()),
                () -> assertEquals(1L, blocklists.get(1).getWhomBaned().getId()),
                () -> assertEquals(2, blocklists.size()));
    }

    @Test
    @DisplayName("Successful showing all bans in selected community")
    void findAllBannedIn() {
        final List<Blocklist> blocklists = blocklistService.findAllBannedIn(3L);
        assertAll(() -> assertEquals(2L, blocklists.get(0).getId()),
                () -> assertEquals(3L, blocklists.get(0).getCommunity().getId()),
                () -> assertEquals(3L, blocklists.get(1).getId()),
                () -> assertEquals(3L, blocklists.get(1).getCommunity().getId()),
                () -> assertEquals(2, blocklists.size()));
    }

    @Test
    @DisplayName("Successful showing all bans in selected time interval")
    void findAllBannsBetween() {
        blocklistService.add(getBlocklist(3L, 5L));
        final List<Blocklist> blocklists = blocklistService.findAllBannsBetween(LocalDate.of(2021, 7, 1), LocalDate.of(2021, 7, 3));
        assertAll(() -> assertEquals(1L, blocklists.get(0).getId()),
                () -> assertEquals(2L, blocklists.get(1).getId()),
                () -> assertEquals(3L, blocklists.get(2).getId()),
                () -> assertEquals(3, blocklists.size()));
    }

    @Test
    @DisplayName("Successful finding ban by it`s id")
    void findByIdSuccess() {
        assertEquals(blocklistService.add(getBlocklist(3L, 4L)), blocklistService.findById(4L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing ban by id")
    void findByIdException() {
        assertThatThrownBy(() -> blocklistService.findById(13L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding ban by community id and user id")
    void findByCommunityIdAndUserIdSuccess() {
        assertEquals(blocklistService.add(getBlocklist(3L, 4L)), blocklistService.findByCommunityIdAndWhomBanedId(3L, 4L));
    }

    @Test
    @DisplayName("Successful deleting ban")
    void deleteSuccess() {
        blocklistService.delete(2L);
        assertThatThrownBy(() -> blocklistService.findById(2L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Exception when we trying to delete ban in not our community")
    void deleteNoSuchId() {
        assertThatThrownBy(() -> blocklistService.delete(1L)).isInstanceOf(TryingModifyNotYourDataException.class);
    }
}
