package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.AIService;
import com.LGDXSCHOOL._dx.service.DynamoDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final AIService aiService;
    private final DynamoDBService dynamoDBService;
    private static final AtomicInteger chatNoGenerator = new AtomicInteger(1); // 고유 번호 생성기

    public ChatController(AIService aiService, DynamoDBService dynamoDBService) {
        this.aiService = aiService;
        this.dynamoDBService = dynamoDBService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleMessage(ChatMessage message) {
        // `CHAT_NO`가 없으면 생성
        if (message.getChatNo() == 0) {
            message.setChatNo((int) (System.currentTimeMillis() / 1000L));
        }

        // 기본 필드 설정
        message.setCreatedAt(LocalDateTime.now().toString());
        message.setSender("ME");
        message.setType("text");
        message.setReadStatus("N");

        // DynamoDB에 저장
        dynamoDBService.saveMessage(message);

        // AI 응답 생성
        String aiResponse = aiService.getAIResponse(message.getContent());
        ChatMessage aiMessage = new ChatMessage();
        aiMessage.setChatNo((int) (System.currentTimeMillis() / 1000L) + 1);
        aiMessage.setContent(aiResponse);
        aiMessage.setSender("AI");
        aiMessage.setType("text");
        aiMessage.setCreatedAt(LocalDateTime.now().toString());
        aiMessage.setReadStatus("N");

        // AI 응답 저장
        dynamoDBService.saveMessage(aiMessage);

        return aiMessage;
    }
}