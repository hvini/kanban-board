package com.localhost.kanbanboard.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.localhost.kanbanboard.entity.CardEntity;
import org.junit.jupiter.api.BeforeEach;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * CardRepositoryTest
 */
@SpringBootTest
@Transactional
public class CardRepositoryTest {
    @Autowired
    private CardRepository cardRepository;
    private CardEntity card;

    @BeforeEach
    public void setUp() {
        card = new CardEntity();
        card.setName("teste");
        card.setPosition(1.0);
    }

    @Test
    public void cardCanBePersisted() {
        // given
        cardRepository.save(card);

        // when
        CardEntity searched = cardRepository.getOne(card.getCardId());

        // then
        assertNotNull(searched);
    }

    @Test
    public void cardCanBeUpdated() {
        // given
        cardRepository.save(card);

        // when
        CardEntity searched = cardRepository.getOne(card.getCardId());
        searched.setName("teste2");
        cardRepository.save(searched);

        // then
        assertNotEquals("teste", searched.getName());
    }

    @Test
    public void cardCanBeRemoved() {
        // given
        cardRepository.save(card);

        // when
        cardRepository.delete(card);
        Optional<CardEntity> searched = cardRepository.findById(card.getCardId());

        // then
        assertTrue(searched.isEmpty());
    }
}