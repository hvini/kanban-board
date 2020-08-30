package com.localhost.kanbanboard.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import com.localhost.kanbanboard.entity.RoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;

/**
 * RoleRepositoryTest
 */
@SpringBootTest
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    RoleEntity role;

    @BeforeEach
    public void setUp() {
        role = new RoleEntity();
        role.setName("teste");
    }

    @Test
    public void roleCanBePersisted() {
        //then
        assertNotNull(roleRepository.save(role));
    }

    @Test
    public void roleCanBeFoundedById() {
        //given
        roleRepository.save(role);

        //when
        RoleEntity searched = roleRepository.getOne(role.getRoleId());

        //then
        assertNotNull(searched);
    }

    @Test
    public void rolesCanBeList() {
        //given
        roleRepository.save(role);

        //when
        List<RoleEntity> roles = roleRepository.findAll();

        //then
        assertNotEquals(true, roles.isEmpty());
    }

    @Test
    public void roleCanBeRemoved() {
        //given
        roleRepository.save(role);
        RoleEntity searched = roleRepository.getOne(role.getRoleId());

        //when
        roleRepository.delete(searched);
        Optional<RoleEntity> removed = roleRepository.findById(searched.getRoleId());

        //then
        assertNotEquals(true, removed.isPresent());
    }
}