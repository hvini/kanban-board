package com.localhost.kanbanboard.handler.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.localhost.kanbanboard.exception.ApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * RestAuthenticationEntryPoint
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        ApiException apiException = new ApiException(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "Unauthorized!.");
        ObjectMapper mapper       = new ObjectMapper();
        
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(mapper.writeValueAsString(apiException));
    }
}