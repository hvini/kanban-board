package com.localhost.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @NotEmpty(message = "Nome deve ser informado!.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @Getter(onMethod = @__(@JsonIgnore))
    private UserEntity user;

    @ManyToOne()
    @JoinColumn(name = "boardId", nullable = false)
    private BoardEntity board;
}