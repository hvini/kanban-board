package com.localhost.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.FetchMode;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.annotations.Fetch;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * UserEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotEmpty(message = "Nome deve ser informado!.")
    private String fullName;

    @NotEmpty(message = "Email deve ser informado!.")
    private String email;

    @NotEmpty(message = "Senha deve ser informada!.")
    private String password;

    private Boolean isEnabled;

    @OneToMany(mappedBy = "user")
    @Getter(onMethod = @__(@JsonIgnore))
    private List<ConfirmationTokenEntity> confirmationToken;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<BoardEntity> boards;

    @ElementCollection
    @CollectionTable(name = "favorite_boards")
    private List<BoardEntity> favoriteBoards;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "user")
    @Getter(onMethod = @__(@JsonIgnore))
    private List<BoardInvitationEntity> boardInvitations;

    public UserEntity() {
        this.boards = new ArrayList<>();
        this.favoriteBoards = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.boardInvitations = new ArrayList<>();
    }

    public void addConfirmationToken(ConfirmationTokenEntity confirmationToken) {
        this.confirmationToken.add(confirmationToken);
    }

    public void addFavoriteBoard(BoardEntity board) {
        this.favoriteBoards.add(board);
    }

    public void removeFavoriteBoard(BoardEntity board) {
        this.favoriteBoards.remove(board);
    }
}