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

/**
 * ListEntityTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ListEntityTest {
    @Autowired
    private TestEntityManager em;
    private ListEntity list;
    private BoardEntity board;

    @BeforeEach
    public void setUp() {
        board = new BoardEntity();
        board.setName("test");
        em.persist(board);

        list = new ListEntity();
        list.setName("test");
        list.setPosition(1.0);
        list.setBoard(board);
    }

    @Test
    public void ListEntityCanBeCreated() {
        // given
        ListEntity saved = em.persistFlushFind(list);

        // when
        ListEntity searched = em.find(ListEntity.class, saved.getListId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void ListPositionCanBeUpdated() {
        // given
        ListEntity saved = em.persistFlushFind(list);

        // when
        saved.setPosition(1.5);
        em.persist(saved);

        // then
        assertNotEquals(1.0, saved.getPosition());
    }

    @Test
    public void ListCanBeRemoved() {
        // given
        ListEntity saved = em.persistFlushFind(list);

        // when
        em.remove(saved);

        // then
        assertEquals(em.find(ListEntity.class, saved.getListId()), isNull());
    }
}