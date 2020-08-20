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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

/**
 * UserConfirmationEntityTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserConfirmationEntityTest {
    @Autowired
    private TestEntityManager entityManager;
    private UserConfirmationEntity userConfirmation;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        userConfirmation = new UserConfirmationEntity();
        userConfirmation.setCreatedDate(LocalDateTime.now());
        userConfirmation.setToken("321");

        user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc");
        user.setIsEnabled(false);
    }

    @Test
    public void userConfirmationCanBeCreated() {
        //given
        UserEntity persistedUser = entityManager.persistFlushFind(user);
        userConfirmation.setUser(persistedUser);
        
        //when
        UserConfirmationEntity persistedUserConfirmation = entityManager.persistFlushFind(userConfirmation);

        //then
        assertNotNull(persistedUserConfirmation.getConfirmationId());
        assertEquals(userConfirmation.getCreatedDate(), persistedUserConfirmation.getCreatedDate());
        assertEquals(userConfirmation.getToken(), persistedUserConfirmation.getToken());
        assertEquals(userConfirmation.getUser().getUserId(), persistedUserConfirmation.getUser().getUserId());
    }

    @Test
    public void userConfirmationCanBeUpdated() {
        //given
        UserEntity persistedUser = entityManager.persistFlushFind(user);
        userConfirmation.setUser(persistedUser);
        UserConfirmationEntity copy = entityManager.persistFlushFind(userConfirmation);

        //when
        copy.setToken("abc");
        UserConfirmationEntity updatedCopy = entityManager.persistFlushFind(copy);

        //then
        assertNotEquals("321", updatedCopy.getToken());
    }

    @Test
    public void userConfirmationCanBeRemoved() {
        //given
        UserEntity persistedUser = entityManager.persistFlushFind(user);
        userConfirmation.setUser(persistedUser);
        UserConfirmationEntity copy = entityManager.persistFlushFind(userConfirmation);

        //when
        entityManager.remove(copy);

        //then
        assertEquals(entityManager.find(UserConfirmationEntity.class, copy.getConfirmationId()), isNull());

    }
}