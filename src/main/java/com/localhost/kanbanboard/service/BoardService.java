package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.BoardRepository;
import org.springframework.scheduling.annotation.Async;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.Optional;
import java.time.ZoneId;

/**
 * BoardService
 */
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private BoardInvitationService boardInvitationService;

    public BoardEntity getById(Long boardId) throws ResourceNotFoundException {
        Optional<BoardEntity> board = boardRepository.findById(boardId);

        if(!board.isPresent())
            throw new ResourceNotFoundException("Invalid board identification!.");

        return board.get();
    }

    @Async("threadPoolTaskExecutor")
    public void create(BoardEntity boardEntity, Long userId) throws ResourceNotFoundException {
        UserEntity user = userService.getById(userId);

        boardEntity.addUser(user);
        boardRepository.save(boardEntity);

        roleService.create("admin", user, boardEntity);
    }

    @Async("threadPoolTaskExecutor")
    public void update(BoardEntity boardEntity) throws ResourceNotFoundException {
        BoardEntity board = getById(boardEntity.getBoardId());

        board.setName(boardEntity.getName());
        boardRepository.save(board);
    }

    @Async("threadPoolTaskExecutor")
    public void delete(Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        BoardEntity board = getById(boardId);
        UserEntity user = userService.getById(userId);

        for(int i = 0; i < user.getRoles().size(); i++) {
            if(user.getRoles().get(i).getBoard().equals(board)) {
                if(!user.getRoles().get(i).getName().contains("admin"))
                    throw new MethodArgumentNotValidException("Only the administrator can delete the board!.");
            }
        }

        removeAllConstraints(board);
        boardRepository.delete(board);
    }

    @Async("threadPoolTaskExecutor")
    public void favorite(Long userId, Long boardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        BoardEntity board = getById(boardId);

        userService.addBoardToFavorite(user, board);
    }

    @Async("threadPoolTaskExecutor")
    public void unfavorite(Long userId, Long boardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        BoardEntity board = getById(boardId);

        userService.removeBoardFromFavorite(user, board);
    }

    @Async("threadPoolTaskExecutor")
    public void inviteUserToBoard(String collaboratorEmail, Long boardId) throws IOException, ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getByEmail(collaboratorEmail);
        BoardEntity board = getById(boardId);

        if(board.getUsers().contains(user))
            throw new MethodArgumentNotValidException("User is already a board member!.");

        for(int i = 0; i < board.getBoardInvitations().size(); i++) {
            if(board.getBoardInvitations().get(i).getUser().equals(user))
                throw new MethodArgumentNotValidException("User already has an invitation to this board!.");
        }

        boardInvitationService.create(user, board);

        ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendInvitationEmail(collaboratorEmail, confirmationToken.getToken(), board);
    }

    @Async("threadPoolTaskExecutor")
    public void acceptInvitation(ConfirmationTokenEntity confirmationToken, Long boardId) throws MethodArgumentNotValidException, ResourceNotFoundException {
        UserEntity user = confirmationToken.getUser();
        BoardEntity board = getById(boardId);

        long hours = ChronoUnit.HOURS.between(confirmationToken.getCreatedDate().atZone(ZoneId.systemDefault()), LocalDateTime.now().atZone(ZoneId.systemDefault()));
        if(hours >= 24)
            throw new MethodArgumentNotValidException("Token has expired!.");

        for(int i = 0; i < user.getBoardInvitations().size(); i++) {
            if(board.getBoardInvitations().get(i).getUser().equals(user)) {
                board.addUser(user);
                boardRepository.save(board);
        
                roleService.create("collaborator", user, board);
                
                boardInvitationService.remove(board.getBoardInvitations().get(i));
                confirmationTokenService.removeConfirmationToken(confirmationToken);
                return;
            }
        }
        throw new MethodArgumentNotValidException("User doesn't have an invitation for this board!.");
    }

    private void sendInvitationEmail(String collaboratorEmail, String token, BoardEntity board) throws IOException {
        Content content = new Content("text/html", "You have an invitation to collaborate in a board, to accept it click on the bellow link." + "http://localhost:8080/board/invitation/" + token + "/?boardId=" + board.getBoardId());
        String subject  = "Board Invitation Link!.";
        Email from      = new Email("vhpcavalcanti@outlook.com");
        Email to        = new Email(collaboratorEmail);
        
        emailSenderService.sendEmail(from, subject, to, content);
    }

    private void removeAllConstraints(BoardEntity board) {
        // removing all users role in the board.
        for(int i = 0; i < board.getRoles().size(); i++) {
            roleService.remove(board.getRoles().get(i));
        }
    }
}