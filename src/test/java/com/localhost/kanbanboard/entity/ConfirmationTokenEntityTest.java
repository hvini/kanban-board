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
public class ConfirmationTokenEntityTest {
    @Autowired
    private TestEntityManager entityManager;
    private ConfirmationTokenEntity confirmationToken;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        confirmationToken = new ConfirmationTokenEntity();
        confirmationToken.setCreatedDate(LocalDateTime.now());
        confirmationToken.setToken("321");

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
        confirmationToken.setUser(persistedUser);
        
        //when
        ConfirmationTokenEntity persistedConfirmationToken = entityManager.persistFlushFind(confirmationToken);

        //then
        assertNotNull(persistedConfirmationToken.getConfirmationId());
        assertEquals(confirmationToken.getCreatedDate(), persistedConfirmationToken.getCreatedDate());
        assertEquals(confirmationToken.getToken(), persistedConfirmationToken.getToken());
        assertEquals(confirmationToken.getUser().getUserId(), persistedConfirmationToken.getUser().getUserId());
    }

    @Test
    public void userConfirmationCanBeUpdated() {
        //given
        UserEntity persistedUser = entityManager.persistFlushFind(user);
        confirmationToken.setUser(persistedUser);
        ConfirmationTokenEntity copy = entityManager.persistFlushFind(confirmationToken);

        //when
        copy.setToken("abc");
        ConfirmationTokenEntity updatedCopy = entityManager.persistFlushFind(copy);

        //then
        assertNotEquals("321", updatedCopy.getToken());
    }

    @Test
    public void userConfirmationCanBeRemoved() {
        //given
        UserEntity persistedUser = entityManager.persistFlushFind(user);
        confirmationToken.setUser(persistedUser);
        ConfirmationTokenEntity copy = entityManager.persistFlushFind(confirmationToken);

        //when
        entityManager.remove(copy);

        //then
        assertEquals(entityManager.find(ConfirmationTokenEntity.class, copy.getConfirmationId()), isNull());

    }
}