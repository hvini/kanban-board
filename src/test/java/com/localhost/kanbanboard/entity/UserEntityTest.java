package com.localhost.kanbanboard.entity;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNull;
import org.junit.jupiter.api.Test;

/**
 * UserEntityTest
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserEntityTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void userEntityCanBeCreated() {
        //given
        UserEntity user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc123");
        user.setIsEnabled(false);

        //when
        UserEntity copy = entityManager.persistFlushFind(user);

        //then
        assertNotNull(copy);
    }

    @Test
    public void userEntityCanBeUpdated() {
        //given
        UserEntity user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc123");
        user.setIsEnabled(false);
        UserEntity copy = entityManager.persistFlushFind(user);

        //when
        copy.setIsEnabled(true);
        UserEntity updatedCopy = entityManager.persistFlushFind(copy);

        //then
        assertNotEquals(copy, updatedCopy);
    }

    @Test
    public void userEntityCanBeRemoved() {
        //given
        UserEntity user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc123");
        user.setIsEnabled(false);
        UserEntity copy = entityManager.persistFlushFind(user);

        //when
        entityManager.remove(copy);

        //then
        assertEquals(entityManager.find(UserEntity.class, copy.getUserId()), isNull());
    }
}