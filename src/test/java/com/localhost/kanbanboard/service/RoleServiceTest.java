package com.localhost.kanbanboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.localhost.kanbanboard.repository.RoleRepository;
import com.localhost.kanbanboard.entity.RoleEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

/**
 * RoleServiceTest
 */
@SpringBootTest
public class RoleServiceTest {
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @Test
    public void roleCanBeCreated() {
        //given
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(new RoleEntity());

        //when
        RoleEntity created = roleService.createRoleIfNotFound("admin");

        //then
        assertNotNull(created);
    }
}