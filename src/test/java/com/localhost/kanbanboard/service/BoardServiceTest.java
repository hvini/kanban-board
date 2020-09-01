package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.localhost.kanbanboard.repository.BoardRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.localhost.kanbanboard.entity.BoardEntity;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * BoardServiceTest
 */
@SpringBootTest
public class BoardServiceTest {
    @MockBean
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Test
    public void boardCanBeFoundedById() throws Exception {
        //given
        BoardEntity board = new BoardEntity();
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

        //when
        BoardEntity searched = boardService.getById(1L);

        //then
        assertNotNull(searched);
    }

    @Test
    public void shouldThrowResourceNotFoundIfBoardIdIsInvalid() {
        //when
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            boardService.getById(1L);
        });

        //then
        assertTrue(ex.getMessage().contains("Invalid board identification!."));
    }
}