package com.localhost.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * CommentEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    
    @NotEmpty(message = "Texto deve ser informado!.")
    private String text;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @Getter(onMethod = @__(@JsonIgnore))
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "cardId", nullable = false)
    @Getter(onMethod = @__(@JsonIgnore))
    private CardEntity card;
}