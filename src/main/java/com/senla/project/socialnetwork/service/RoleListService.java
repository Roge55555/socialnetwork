package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.RoleList;
import com.senla.project.socialnetwork.exeptions.NoSuchIdException;
import com.senla.project.socialnetwork.repository.RoleListRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleListService {

    private RoleListRepository roleListRepository;

    public RoleListService(RoleListRepository roleListRepository) {
        this.roleListRepository = roleListRepository;
    }

    public void add(RoleList roleList){
        roleListRepository.save(roleList);
    }

    public List<RoleList> findAll(){
        return roleListRepository.findAll();
    }

    @SneakyThrows
    public RoleList findById(Long id){
        return roleListRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public RoleList update(Long id, RoleList roleList) {

        return roleListRepository.findById(id).map(rl -> {
            rl.setName(roleList.getName());
            return roleListRepository.save(rl);
        })
                .orElseThrow(() ->
                        new NoSuchIdException(id, "role list")
                );
    }

    public void delete(Long id) {
        roleListRepository.deleteById(id);
    }
}
