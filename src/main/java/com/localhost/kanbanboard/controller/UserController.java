package com.localhost.kanbanboard.controller;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import com.localhost.kanbanboard.service.ConfirmationTokenService;
import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import com.localhost.kanbanboard.service.UserService;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiResponse;
import java.io.IOException;
import java.util.List;

/**
 * UserController
 */
@Controller
@RequestMapping("/u")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @ApiOperation(value = "Find all users", notes = "Returns all users in the system")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 401, message = "Not authenticated")
    })
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<UserEntity> user = userService.getAll();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all boards of a user", notes = "Returns all boards of the informed user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping("/{userId}/boards")
    public ResponseEntity<?> getAllBoards(@PathVariable("userId") Long userId) throws Exception {
        try {
            List<BoardEntity> boards = userService.getAllBoards(userId);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Register a user", notes = "Register a new user in the system")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "User created successfully", response = void.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 500, message = "Internal server error"),
    })
    @PostMapping("/register")
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

    @ApiOperation(value = "User email confirmation", notes = "Confirm the user's email")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User email confirmed"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/sign-up/confirm")
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

    @ApiOperation(value = "Password forgot", notes = "Send a link with password reset instructions in the email provided")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Password reset link sent successfully"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/sign-up/forgot")
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

    @ApiOperation(value = "Password reset", notes = "Sets a new user password")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Password reset successfully"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/sign-up/reset-password")
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