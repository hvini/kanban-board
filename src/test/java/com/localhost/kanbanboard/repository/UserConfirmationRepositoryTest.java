package com.localhost.kanbanboard.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.localhost.kanbanboard.entity.UserConfirmationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.localhost.kanbanboard.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * UserConfirmationRepositoryTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserConfirmationRepositoryTest {
    @Autowired
    private UserConfirmationRepository userConfirmationRepository;
    @Autowired
    private UserRepository userRepository;
    private UserConfirmationEntity userConfirmation;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc");
        user.setIsEnabled(false);
        UserEntity savedUser = userRepository.save(user);

        userConfirmation = new UserConfirmationEntity();
        userConfirmation.setCreatedDate(LocalDateTime.now());
        userConfirmation.setToken("123321");
        userConfirmation.setUser(savedUser);
    }

    @Test
    public void userConfirmationCanBePersisted() {
        //then
        assertNotNull(userConfirmationRepository.save(userConfirmation));
    }

    @Test
    public void userConfirmationCanBeFoundedById() {
        //given
        userConfirmationRepository.save(userConfirmation);

        //when
        UserConfirmationEntity copy = userConfirmationRepository.getOne(userConfirmation.getConfirmationId());

        //then
        assertNotNull(copy);
    }

    @Test
    public void userConfirmationCanBeUpdated() {
        //given
        userConfirmationRepository.save(userConfirmation);
        UserConfirmationEntity copy = userConfirmationRepository.getOne(userConfirmation.getConfirmationId());

        //when
        copy.setToken("321123");

        //then
        assertNotEquals("123321", copy.getToken());
    }

    @Test
    public void userConfirmationsCanBeListed() {
        //given
        userConfirmationRepository.save(userConfirmation);

        //when
        List<UserConfirmationEntity> userConfirmations = userConfirmationRepository.findAll();

        //then
        assertNotEquals(true, userConfirmations.isEmpty());
    }

    @Test
    public void userConfirmationCanBeRemoved() {
        //given
        userConfirmationRepository.save(userConfirmation);
        UserConfirmationEntity copy = userConfirmationRepository.getOne(userConfirmation.getConfirmationId());

        //when
        userConfirmationRepository.delete(copy);
        Optional<UserConfirmationEntity> removedCopy = userConfirmationRepository.findById(copy.getConfirmationId());

        //then
        assertNotEquals(true, removedCopy.isPresent());
    }
}