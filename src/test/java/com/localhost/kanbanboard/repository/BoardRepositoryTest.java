package com.localhost.kanbanboard.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import com.localhost.kanbanboard.entity.BoardEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * BoardRepositoryTest
 */
@SpringBootTest
@Transactional
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    private BoardEntity board;

    @BeforeEach
    public void setUp() {
        board = new BoardEntity();
        board.setName("teste");
        board.setIsFavorite(false);
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