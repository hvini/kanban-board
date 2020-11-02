package com.localhost.kanbanboard.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * WebSocketController
 */
@Controller
public class WebSocketController {
    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public void sendMessage(String message) {
        System.out.println(message);
        this.template.convertAndSend("/topic/public", message);
    }
}