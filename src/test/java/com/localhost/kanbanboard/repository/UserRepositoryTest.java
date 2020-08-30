package com.localhost.kanbanboard.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import com.localhost.kanbanboard.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;

/**
 * UserRepositoryTest
 */
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc");
        user.setIsEnabled(false);
    }

    @Test
    public void userCanBePersisted() {
        //then
        assertNotNull(userRepository.save(user));
    }

    @Test
    public void userCanBeFoundedById() {
        //given
        userRepository.save(user);

        //when
        UserEntity copy = userRepository.getOne(user.getUserId());

        //then
        assertNotNull(copy);
    }

    @Test
    public void userCanBeUpdated() {
        //given
        userRepository.save(user);
        UserEntity copy = userRepository.getOne(user.getUserId());

        //when
        copy.setIsEnabled(true);

        //then
        assertNotEquals(false, copy.getIsEnabled());
    }

    @Test
    public void usersCanBeListed() {
        //given
        userRepository.save(user);

        //when
        List<UserEntity> users = userRepository.findAll();

        //then
        assertNotEquals(true, users.isEmpty());
    }

    @Test
    public void userCanBeRemoved() {
        //given
        userRepository.save(user);
        UserEntity copy = userRepository.getOne(user.getUserId());

        //when
        userRepository.delete(copy);
        Optional<UserEntity> removedCopy = userRepository.findById(user.getUserId());

        //then
        assertNotEquals(true, removedCopy.isPresent());
    }
}