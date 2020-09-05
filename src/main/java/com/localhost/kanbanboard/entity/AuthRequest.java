package com.localhost.kanbanboard.entity;

import javax.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * AuthRequest
 */
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AuthRequest {
    @NotEmpty(message = "Email deve ser informado!.")
    private String email;
    
    @NotEmpty(message = "Senha deve ser informada!.")
    private String password;

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}