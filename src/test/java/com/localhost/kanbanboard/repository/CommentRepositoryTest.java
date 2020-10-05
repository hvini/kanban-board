package com.localhost.kanbanboard.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.localhost.kanbanboard.entity.CommentEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * CommentRepositoryTest
 */
@SpringBootTest
@Transactional
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    private CommentEntity comment;

    @BeforeEach
    public void setUp() {
        comment = new CommentEntity();
        comment.setText("teste");
    }

    @Test
    public void commentCanBePersisted() {
        // given
        commentRepository.save(comment);

        // when
        CommentEntity searched = commentRepository.getOne(comment.getCommentId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void commentCanBeUpdated() {
        // given
        commentRepository.save(comment);

        // when
        CommentEntity searched = commentRepository.getOne(comment.getCommentId());
        searched.setText("teste2");
        commentRepository.save(searched);

        // then
        assertNotEquals("teste", searched.getText());
    }

    @Test
    public void commentCanBeRemoved() {
        // given
        commentRepository.save(comment);

        // when
        commentRepository.delete(comment);
        Optional<CommentEntity> searched = commentRepository.findById(comment.getCommentId());

        // then
        assertTrue(searched.isEmpty());
    }
}