package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.RoleListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor//TODO:разница ток в проверке на нул - @RequiredArgsConstructor
public class RoleListService {

    private final RoleListRepository roleListRepository;

    public RoleList add(RoleList roleList) {
        roleList.setId(null);
        return roleListRepository.save(roleList);
    }

    public List<RoleList> findAll() {
        return roleListRepository.findAll();
    }

    public RoleList findById(Long id) {
        return roleListRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public RoleList update(Long id, RoleList roleList) {

        return roleListRepository.findById(id).map(rl -> {
            rl.setName(roleList.getName());
            return roleListRepository.save(rl);
        })
                .orElseThrow(() -> new NoSuchElementException(id));
    }

    public void delete(Long id) {
        if (roleListRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException(id);
        }
        roleListRepository.deleteById(id);
    }
}
