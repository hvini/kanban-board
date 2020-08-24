package com.localhost.kanbanboard.controller;

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
        try {
            List<UserEntity> user = userService.getAll();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register/")
    public ResponseEntity<?> register(@RequestBody UserEntity userEntity) {
        try {
            userService.register(userEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-up/")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            String token = userService.authenticate(authRequest);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sign-up/confirm/")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);
        try {
            userService.confirmUser(confirmationToken);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
}