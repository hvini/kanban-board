package com.localhost.kanbanboard.entity;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * RoleEntityTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleEntityTest {
    @Autowired
    private TestEntityManager em;
    private RoleEntity role;

    @BeforeEach
    public void setUp() {
        role = new RoleEntity();
        role.setName("admin");
    }

    @Test
    public void roleCanBeCreated() {
        //given
        RoleEntity saved = em.persistFlushFind(role);

        //when
        RoleEntity searched = em.find(RoleEntity.class, saved.getRoleId());

        //then
        assertNotNull(searched);
    }

    @Test
    public void roleCanBeUpdated() {
        //given
        RoleEntity saved = em.persistFlushFind(role);

        //when
        saved.setName("colaborador");
        RoleEntity updatedSaved = em.persistFlushFind(saved);

        //then
        assertEquals("colaborador", updatedSaved.getName());
    }

    @Test
    public void roleCanBeRemoved() {
        //given
        RoleEntity saved = em.persistFlushFind(role);

        //when
        em.remove(saved);

        //then
        assertEquals(em.find(RoleEntity.class, saved.getRoleId()), isNull());
    }
}