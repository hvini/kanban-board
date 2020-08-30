package com.localhost.kanbanboard.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import com.localhost.kanbanboard.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * UserConfirmationRepositoryTest
 */
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ConfirmationTokenRepositoryTest {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    private ConfirmationTokenEntity confirmationToken;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc");
        user.setIsEnabled(false);
        UserEntity savedUser = userRepository.save(user);

        confirmationToken = new ConfirmationTokenEntity();
        confirmationToken.setCreatedDate(LocalDateTime.now());
        confirmationToken.setToken("123321");
        confirmationToken.setUser(savedUser);
    }

    @Test
    public void userConfirmationCanBePersisted() {
        //then
        assertNotNull(confirmationTokenRepository.save(confirmationToken));
    }

    @Test
    public void userConfirmationCanBeFoundedById() {
        //given
        confirmationTokenRepository.save(confirmationToken);

        //when
        ConfirmationTokenEntity copy = confirmationTokenRepository.getOne(confirmationToken.getConfirmationId());

        //then
        assertNotNull(copy);
    }

    @Test
    public void userConfirmationCanBeUpdated() {
        //given
        confirmationTokenRepository.save(confirmationToken);
        ConfirmationTokenEntity copy = confirmationTokenRepository.getOne(confirmationToken.getConfirmationId());

        //when
        copy.setToken("321123");

        //then
        assertNotEquals("123321", copy.getToken());
    }

    @Test
    public void userConfirmationsCanBeListed() {
        //given
        confirmationTokenRepository.save(confirmationToken);

        //when
        List<ConfirmationTokenEntity> userConfirmations = confirmationTokenRepository.findAll();

        //then
        assertNotEquals(true, userConfirmations.isEmpty());
    }

    @Test
    public void userConfirmationCanBeRemoved() {
        //given
        confirmationTokenRepository.save(confirmationToken);
        ConfirmationTokenEntity copy = confirmationTokenRepository.getOne(confirmationToken.getConfirmationId());

        //when
        confirmationTokenRepository.delete(copy);
        Optional<ConfirmationTokenEntity> removedCopy = confirmationTokenRepository.findById(copy.getConfirmationId());

        //then
        assertNotEquals(true, removedCopy.isPresent());
    }
}