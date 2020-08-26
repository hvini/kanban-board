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
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;
import com.localhost.kanbanboard.util.JwtUtil;
import javax.naming.AuthenticationException;
import java.time.temporal.ChronoUnit;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.io.IOException;
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

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getById(Long userId) throws ResourceNotFoundException {
        Optional<UserEntity> user = userRepository.findById(userId);

        if(!user.isPresent())
            throw new ResourceNotFoundException("Invalid user identification!.");

        return user.get();
    }

    public UserEntity getByEmail(String email) throws ResourceNotFoundException {
        UserEntity user = userRepository.findByEmail(email);

        if(user == null)
            throw new ResourceNotFoundException("There is no user with this email!.");

        return user;
    }

    public void register(UserEntity user) throws MethodArgumentNotValidException, IOException {
        String encryptedPassword = createPasswordHash(user.getPassword());

        UserEntity userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail != null)
            throw new MethodArgumentNotValidException("This email is already taken!.");

        user.setIsEnabled(false);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendConfirmationMail(user.getEmail(), confirmationToken.getToken());
    }

    public void confirmUser(ConfirmationTokenEntity confirmationToken) throws MethodArgumentNotValidException {
        UserEntity user = confirmationToken.getUser();

        long hours = ChronoUnit.HOURS.between(confirmationToken.getCreatedDate(), LocalDateTime.now());
        if(hours >= 24)
            throw new MethodArgumentNotValidException("Token has expired!.");

        if(user.getIsEnabled())
            throw new MethodArgumentNotValidException("User is already confirmed!.");

        user.setIsEnabled(true);
        userRepository.save(user);

        confirmationTokenService.removeConfirmationToken(confirmationToken);
    }

    public String authenticate(AuthRequest authRequest) throws AuthenticationException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        
        return jwtUtil.generateToken(authRequest.getEmail());
    }

    public void forgotPassword(String email) throws IOException, ResourceNotFoundException {
        UserEntity user = getByEmail(email);

        ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendPasswordResetEmail(user.getEmail(), confirmationToken.getToken());
    }

    public void resetPassword(ConfirmationTokenEntity confirmationToken, String password) throws MethodArgumentNotValidException {
        UserEntity user = confirmationToken.getUser();

        long hours = ChronoUnit.HOURS.between(confirmationToken.getCreatedDate(), LocalDateTime.now());
        if(hours >= 24)
            throw new MethodArgumentNotValidException("Token has expired!.");

        String encryptedPassword = createPasswordHash(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        confirmationTokenService.removeConfirmationToken(confirmationToken);
    }
    
    private void sendConfirmationMail(String userMail, String token) throws IOException {
        Content content = new Content("text/html", "Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8080/sign-up/confirm/?token=" + token);
        String subject  = "Mail Confirmation Link!.";
        Email from      = new Email("vhpcavalcanti@outlook.com");
        Email to        = new Email(userMail);
        
        emailSenderService.sendEmail(from, subject, to, content);
    }

    private void sendPasswordResetEmail(String userMail, String token) throws IOException {
        Content content = new Content("text/html", "You recently requested to reset your password. Please click on the below link to reset it." + "http://localhost:8080/sign-up/reset-password/?token=" + token);
        String subject  = "Password Reset Link!";
        Email from      = new Email("vhpcavalcanti@outlook.com");
        Email to        = new Email(userMail);
        
        emailSenderService.sendEmail(from, subject, to, content);
    }

    private String createPasswordHash(String password) {
        String encodedPassword = null;
        int strength           = 10;

        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());

        encodedPassword = bCryptEncoder.encode(password);
        return encodedPassword;
    }
}