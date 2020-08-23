package com.localhost.kanbanboard.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.UserRepository;
import com.localhost.kanbanboard.entity.AuthRequest;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.localhost.kanbanboard.util.JwtUtil;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

/**
 * UserService
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public List<UserEntity> getAll() throws Exception {
        List<UserEntity> users = userRepository.findAll();

        if(users.isEmpty())
            throw new ResourceNotFoundException("There are no registered users!.");

        return users;
    }

    public UserEntity getById(Long userId) throws Exception {
        Optional<UserEntity> user = userRepository.findById(userId);

        if(!user.isPresent())
            throw new ResourceNotFoundException("Invalid user identification!.");

        return user.get();
    }

    public void register(UserEntity user) {
        String encryptedPassword = createPasswordHash(user.getPassword());

        user.setIsEnabled(false);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendConfirmationMail(user.getEmail(), confirmationToken.getToken());
    }

    public void confirmUser(ConfirmationTokenEntity confirmationToken) throws Exception {
        UserEntity user = confirmationToken.getUser();

        if(user.getIsEnabled())
            throw new MethodArgumentNotValidException("User is already confirmed!.");

        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public String authenticate(AuthRequest authRequest) throws Exception {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        return jwtUtil.generateToken(authRequest.getEmail());
    }
    
    private void sendConfirmationMail(String userMail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("no-reply@kanbanboard.com");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8080/sign-up/confirm?token=" + token);
        emailSenderService.sendEmail(mailMessage);
    }

    private String createPasswordHash(String password) {
        String encodedPassword = null;
        int strength           = 10;

        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

        encodedPassword = bCryptEncoder.encode(password);
        return encodedPassword;
    }
}