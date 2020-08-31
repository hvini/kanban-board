package com.localhost.kanbanboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.RoleRepository;
import com.localhost.kanbanboard.entity.RoleEntity;
import org.springframework.stereotype.Service;

/**
 * RoleService
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleEntity createRoleIfNotFound(String name) {
        RoleEntity role = roleRepository.findByName(name);
        if(role == null) {
            role = new RoleEntity();
            role.setName(name);
            roleRepository.save(role);
        }
        return role;
    }
}