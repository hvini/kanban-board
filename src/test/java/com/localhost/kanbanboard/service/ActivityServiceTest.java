package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.localhost.kanbanboard.entity.ActivityEntity;
import com.localhost.kanbanboard.entity.BoardEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

/**
 * ActivityServiceTest
 */
@SpringBootTest
public class ActivityServiceTest {
    @MockBean
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityService activityService;

    @Test
    public void activityCanBeCreated() {
        // given
        BoardEntity board = mock(BoardEntity.class);
        when(activityRepository.save(any(ActivityEntity.class))).thenReturn(new ActivityEntity());

        // when
        activityService.create("test", board);

        // then
        verify(activityRepository).save(any(ActivityEntity.class));
    }

    @Test
    public void activityEntityCanBeDeleted() {
        // given
        ActivityEntity activity = mock(ActivityEntity.class);
        doNothing().when(activityRepository).delete(any(ActivityEntity.class));

        // when
        activityService.delete(activity);

        // then
        verify(activityRepository).delete(any(ActivityEntity.class));
    }
}