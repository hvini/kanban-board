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
 * CommentEntityTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CommentEntityTest {
    @Autowired
    private TestEntityManager em;
    private CommentEntity comment;
    private CardEntity card;
    private UserEntity user;
    private ListEntity list;
    private BoardEntity board;

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
        em.persist(card);

        user = new UserEntity();
        user.setEmail("teste");
        user.setPassword("abc123");
        user.setFullName("teste");
        em.persist(user);

        comment = new CommentEntity();
        comment.setText("teste");
        comment.setUser(user);
        comment.setCard(card);
    }

    @Test
    public void commentEntityCanBeCreated() {
        // given
        CommentEntity saved = em.persistFlushFind(comment);

        // when
        CommentEntity searched = em.find(CommentEntity.class, saved.getCommentId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void commentEntityCanBeRemoved() {
        // given
        CommentEntity saved = em.persistFlushFind(comment);

        // when
        em.remove(saved);

        // then
        assertEquals(em.find(CommentEntity.class, saved.getCommentId()), isNull());
    }
}