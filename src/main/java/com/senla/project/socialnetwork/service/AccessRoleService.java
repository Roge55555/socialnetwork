package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.repository.AccessRoleRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessRoleService {

    private AccessRoleRepository accessRoleRepository;

    public AccessRoleService(AccessRoleRepository accessRoleRepository) {
        this.accessRoleRepository = accessRoleRepository;
    }

    public void add(AccessRole accessRole){
        accessRoleRepository.save(accessRole);
    }

    public List<AccessRole> findAll(){
        return accessRoleRepository.findAll();
    }

    @SneakyThrows
    public AccessRole findById(Long id){
        return accessRoleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public AccessRole update(Long id, AccessRole accessRole) {

        return accessRoleRepository.findById(id).map(ar -> {
            ar.setRole_name(accessRole.getRole_name());
            return accessRoleRepository.save(ar);
        })
                .orElseThrow(() ->
                        new IllegalArgumentException()//new NoSuchIdException(id, "update")
                );
    }

    public void delete(Long id) {
        accessRoleRepository.deleteById(id);
    }
}
