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
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * ListEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long listId;

    @NotEmpty(message = "Nome deve ser informado!.")
    private String name;

    private Double position;

    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false)
    @Getter(onMethod = @__(@JsonIgnore))
    private BoardEntity board;

    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<CardEntity> cards;
    

    public ListEntity() {
        this.cards = new ArrayList<>();
    }
}