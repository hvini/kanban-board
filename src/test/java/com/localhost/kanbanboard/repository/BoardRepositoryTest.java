package com.localhost.kanbanboard.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.RoleEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

/**
 * BoardRepositoryTest
 */
@SpringBootTest
@Transactional
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    private BoardEntity board;
    private RoleEntity role;
    private UserEntity user;
    private Map<UserEntity, RoleEntity> boardRole;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setFullName("Vinícius Cavalcanti");
        user.setEmail("vhpcavalcanti@outlook.com");
        user.setPassword("abc");
        user.setIsEnabled(false);
        userRepository.save(user);

        role = new RoleEntity();
        role.setName("admin");
        roleRepository.save(role);

        boardRole = new HashMap<UserEntity, RoleEntity>();
        boardRole.put(user, role);
        
        board = new BoardEntity();
        board.setName("teste");
        board.setIsFavorite(false);
        board.setBoardRole(boardRole);
    }

    @Test
    public void boardCanBePersisted() {
        //then
        assertNotNull(boardRepository.save(board));
    }

    @Test
    public void boardsCanBeFoundedById() {
        //given
        boardRepository.save(board);

        //when
        BoardEntity searched = boardRepository.getOne(board.getBoardId());

        //then
        assertNotNull(searched);
    }

    @Test
    public void boardCanBeUpdated() {
        //given
        boardRepository.save(board);
        Optional<BoardEntity> searched = boardRepository.findById(board.getBoardId());

        //when
        searched.get().setIsFavorite(true);

        //then
        assertNotEquals(false, searched.get().getIsFavorite());
    }

    @Test
    public void boardCanBeRemoved() {
        //given
        boardRepository.save(board);
        BoardEntity searched = boardRepository.getOne(board.getBoardId());

        //when
        boardRepository.delete(searched);
        Optional<BoardEntity> removed = boardRepository.findById(searched.getBoardId());

        //then
        assertNotEquals(true, removed.isPresent());
    }
}