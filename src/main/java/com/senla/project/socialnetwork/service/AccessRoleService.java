package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.AccessRole;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.model.Role;
import com.senla.project.socialnetwork.repository.AccessRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccessRoleService {

    private AccessRoleRepository accessRoleRepository;

    public void add(AccessRole accessRole) {
        accessRoleRepository.save(accessRole);
    }

    public List<AccessRole> findAll() {
        if (accessRoleRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return accessRoleRepository.findAll();
    }

    public AccessRole findById(Long id) {
        return accessRoleRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public AccessRole findByName(Role name) {
        //TODO: change NoSuchIdException -> NoSuchElementException
        return accessRoleRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    }

    public AccessRole update(Long id, AccessRole accessRole) {

        return accessRoleRepository.findById(id).map(ar -> {
            ar.setName(accessRole.getName());
            return accessRoleRepository.save(ar);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (accessRoleRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        accessRoleRepository.deleteById(id);
    }
}