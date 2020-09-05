package com.localhost.kanbanboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import com.localhost.kanbanboard.repository.BoardRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.localhost.kanbanboard.repository.RoleRepository;
import com.localhost.kanbanboard.repository.UserRepository;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.RoleEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * RoleServiceTest
 */
@SpringBootTest
public class RoleServiceTest {
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BoardRepository boardRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;
    
    @Test
    public void roleCanBeCreated() throws Exception {
        //given
        UserEntity user = new UserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserEntity savedUser = userService.getById(1L);

        BoardEntity board = new BoardEntity();
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        BoardEntity savedBoard = boardService.getById(1L);

        when(roleRepository.save(any(RoleEntity.class))).thenReturn(new RoleEntity());

        //when
        RoleEntity role = roleService.create("teste", savedUser, savedBoard);

        //then
        assertNotNull(role);
    }
}