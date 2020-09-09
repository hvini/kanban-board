package com.localhost.kanbanboard.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.ListEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * ListRepositoryTest
 */
@SpringBootTest
@Transactional
public class ListRepositoryTest {
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private BoardRepository boardRepository;
    private ListEntity list;
    private BoardEntity board;

    @BeforeEach
    public void setUp() {
        board = new BoardEntity();
        board.setName("test");
        boardRepository.save(board);

        list = new ListEntity();
        list.setName("test");
        list.setPosition(1.0);
        list.setBoard(board);
    }

    @Test
    public void ListCanBePersisted() {
        // given
        listRepository.save(list);

        // when
        ListEntity searched = listRepository.getOne(list.getListId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void ListCanBeUpdated() {
        // given
        listRepository.save(list);

        // when
        ListEntity searched = listRepository.getOne(list.getListId());
        searched.setPosition(1.5);
        listRepository.save(searched);

        // then
        assertNotEquals(1.0, searched.getPosition());
    }

    @Test
    public void ListCanBeRemoved() {
        // given
        listRepository.save(list);

        // when
        listRepository.delete(list);
        Optional<ListEntity> searched = listRepository.findById(list.getListId());

        // then
        assertTrue(searched.isEmpty());
    }
}