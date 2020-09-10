package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.entity.ActivityEntity;
import com.localhost.kanbanboard.entity.BoardEntity;
import org.springframework.stereotype.Service;

/**
 * ActivityService
 */
@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public void create(String text, BoardEntity board) {
        ActivityEntity activity = new ActivityEntity();

        activity.setText(text);
        activity.setBoard(board);
        activityRepository.save(activity);
    }

    public void delete(ActivityEntity activity) {
        activityRepository.delete(activity);
    }
}