package com.localhost.kanbanboard.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.localhost.kanbanboard.entity.ActivityEntity;
import com.localhost.kanbanboard.entity.BoardEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * ActivityRepositoryTest
 */
@SpringBootTest
@Transactional
public class ActivityRepositoryTest {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private BoardRepository boardRepository;
    private ActivityEntity activity;
    private BoardEntity board;

    @BeforeEach
    public void setUp() {
        board = new BoardEntity();
        board.setName("test");
        boardRepository.save(board);

        activity = new ActivityEntity();
        activity.setText("test");
        activity.setBoard(board);
    }

    @Test
    public void ActivityCanBePersisted() {
        // given
        activityRepository.save(activity);

        // when
        ActivityEntity searched = activityRepository.getOne(activity.getActivityId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void ActivityCanBeUpdated() {
        // given
        activityRepository.save(activity);

        // when
        ActivityEntity searched = activityRepository.getOne(activity.getActivityId());
        searched.setBoard(activity.getBoard());
        searched.setText("test2");
        activityRepository.save(searched);

        // then
        assertNotEquals("test", searched.getText());
    }

    @Test
    public void ActivityCanBeRemoved() {
        // given
        activityRepository.save(activity);

        // when
        activityRepository.delete(activity);
        Optional<ActivityEntity> searched = activityRepository.findById(activity.getActivityId());

        // then
        assertTrue(searched.isEmpty());
    }
}