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
 * ActivityEntityTest
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ActivityEntityTest {
    @Autowired
    private TestEntityManager em;
    private ActivityEntity activity;
    private BoardEntity board;

    @BeforeEach
    public void setUp() {
        board = new BoardEntity();
        board.setName("test");
        em.persist(board);

        activity = new ActivityEntity();
        activity.setText("test");
        activity.setBoard(board);
    }

    @Test
    public void activityEntityCanBeCreated() {
        // given
        ActivityEntity saved = em.persistFlushFind(activity);

        // when
        ActivityEntity searched = em.find(ActivityEntity.class, saved.getActivityId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void activityEntityCanBeRemoved() {
        // given
        ActivityEntity saved = em.persistFlushFind(activity);

        // when
        em.remove(saved);

        // then
        assertEquals(em.find(ActivityEntity.class, saved.getActivityId()), isNull());
    }
}