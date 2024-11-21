package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.AIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final AIService aiService;

    public ChatController(AIService aiService) {
        this.aiService = aiService; // AIService 주입
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleMessage(ChatMessage message) {
        logger.info("Received message: {}", message);

        // 사용자가 보낸 메시지
        ChatMessage userMessage = new ChatMessage();
        userMessage.setSender("You");
        userMessage.setContent(message.getContent());
        userMessage.setType("text");

        logger.info("User message: {}", userMessage);

        // AI 응답 생성
        String aiResponse = aiService.getAIResponse(message.getContent());
        ChatMessage aiMessage = new ChatMessage();
        aiMessage.setSender("AI");
        aiMessage.setContent(aiResponse);
        aiMessage.setType("text");

        logger.info("AI message: {}", aiMessage);

        return aiMessage;
    }
}
