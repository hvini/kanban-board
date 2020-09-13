package com.localhost.kanbanboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.RoleRepository;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.RoleEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;

/**
 * RoleService
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleEntity create(String name, UserEntity user, BoardEntity board) {
        RoleEntity role = new RoleEntity();

        role.setName(name);
        role.setUser(user);
        role.setBoard(board);
        roleRepository.save(role);
        
        return role;
    }
}