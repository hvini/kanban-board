package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.repository.ConfirmationTokenRepository;
import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ConfirmationTokenService
 */
@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenEntity getByToken(String token) {
        ConfirmationTokenEntity confirmationToken = confirmationTokenRepository.findByToken(token);

        return confirmationToken;
    }

    public void saveConfirmationToken(ConfirmationTokenEntity confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }
}