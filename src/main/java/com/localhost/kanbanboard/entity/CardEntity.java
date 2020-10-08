package com.localhost.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.FetchMode;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.annotations.Fetch;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * CardEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardId;

    @NotEmpty(message = "Nome deve ser informado!.")
    private String name;
    
    private String description;
    
    private Double position;
    
    private LocalDateTime createdDate;
    
    private LocalDateTime dueDate;
    
    private Boolean isFinished;
    
    private LocalDateTime finishedDate;
    
    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "listId", nullable = false)
    @Getter(onMethod = @__(@JsonIgnore))
    private ListEntity list;

    public CardEntity() {
        this.comments = new ArrayList<>();
    }
}