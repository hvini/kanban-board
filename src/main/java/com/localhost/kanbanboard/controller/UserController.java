package com.localhost.kanbanboard.controller;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import com.localhost.kanbanboard.exception.BadCredentialsException;
import com.localhost.kanbanboard.service.ConfirmationTokenService;
import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import com.localhost.kanbanboard.service.UserService;
import com.localhost.kanbanboard.entity.AuthRequest;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.util.List;

/**
 * UserController
 */
@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/users/")
    public ResponseEntity<?> getAll() {
        List<UserEntity> user = userService.getAll();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register/")
    public ResponseEntity<?> register(@RequestBody UserEntity userEntity) throws Exception {
        try {
            userService.register(userEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(IOException ex) {
            throw new IOException(ex.getLocalizedMessage(), ex);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/sign-up/")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            String token = userService.authenticate(authRequest);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch(Exception ex) {
            throw new BadCredentialsException("Invalid email or password!.", ex);
        }
    }

    @GetMapping("/sign-up/confirm/")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) throws Exception {
        try {
            ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);

            userService.confirmUser(confirmationToken);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/sign-up/forgot/")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws Exception {
        try {
            userService.forgotPassword(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IOException ex) {
            throw new IOException(ex.getLocalizedMessage(), ex);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/sign-up/reset-password/")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) throws Exception {
        try {
            ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);
            
            userService.resetPassword(confirmationToken, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        }
    }
}