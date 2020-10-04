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
 * CardEntityTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CardEntityTest {
    @Autowired
    private TestEntityManager em;
    private BoardEntity board;
    private ListEntity list;
    private CardEntity card;

    @BeforeEach
    public void setUp() {
        board = new BoardEntity();
        board.setName("teste");
        em.persist(board);

        list = new ListEntity();
        list.setName("teste");
        list.setPosition(1.0);
        list.setBoard(board);
        em.persist(list);

        card = new CardEntity();
        card.setName("teste");
        card.setPosition(1.0);
        card.setList(list);
    }

    @Test
    public void cardEntityCanBeCreated() {
        // given
        CardEntity saved = em.persistFlushFind(card);

        // when
        CardEntity searched = em.find(CardEntity.class, saved.getCardId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void cardEntityCanBeRemoved() {
        // given
        CardEntity saved = em.persistFlushFind(card);

        // when
        em.remove(saved);

        // then
        assertEquals(em.find(CardEntity.class, saved.getCardId()), isNull());
    }
}