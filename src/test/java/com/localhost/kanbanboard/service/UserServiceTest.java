package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.localhost.kanbanboard.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.localhost.kanbanboard.entity.UserEntity;
import static org.mockito.Mockito.doReturn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

/**
 * UserServiceTest
 */
@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc321");
        user.setIsEnabled(false);
    }

    @Test
    public void usersCanBeListed() throws Exception {
        //given
        doReturn(Arrays.asList(user)).when(userRepository).findAll();

        //when
        List<UserEntity> users = userService.getAll();

        //then
        assertEquals(1, users.size());
    }

    @Test
    public void userCanBeFoundedById() throws Exception {
        //given
        UserEntity expected = new UserEntity();
        doReturn(Optional.of(expected)).when(userRepository).findById(1L);

        //when
        UserEntity returned = userService.getById(1L);

        //then
        assertNotNull(returned);
    }

    @Test
    public void shouldThrowResourceNotFoundIfUserIdIsInvalid() {
        //when
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getById(0L);
        });

        //then
        assertTrue(ex.getMessage().contains("Invalid user identification!."));
    }
}