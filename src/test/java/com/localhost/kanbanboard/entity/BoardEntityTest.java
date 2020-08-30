package com.localhost.kanbanboard.entity;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * BoardEntityTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BoardEntityTest {
    @Autowired
    private TestEntityManager em;
    private BoardEntity board;

    @BeforeEach
    public void setUp() {
        board = new BoardEntity();
        board.setName("teste");
        board.setIsFavorite(false);
    }

    @Test
    public void boardEntityCanBeCreated() {
        //given
        BoardEntity saved = em.persistFlushFind(board);

        //when
        BoardEntity searched = em.find(BoardEntity.class, saved.getBoardId());

        //then
        assertNotNull(searched);
    }

    @Test
    public void boardEntityCanBeUpdated() {
        //given
        BoardEntity saved = em.persistFlushFind(board);

        //when
        saved.setIsFavorite(true);
        BoardEntity updatedSaved = em.persistFlushFind(saved);

        //then
        assertEquals(true, updatedSaved.getIsFavorite());
    }

    @Test
    public void boardEntityCanBeRemoved() {
        //given
        BoardEntity saved = em.persistFlushFind(board);

        //when
        em.remove(saved);

        //then
        assertEquals(em.find(BoardEntity.class, saved.getBoardId()), isNull());
    }
}