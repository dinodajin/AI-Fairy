package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.AIService;
import com.LGDXSCHOOL._dx.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ChatMessageController {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageController.class);

    private final AIService aiService;
    private final ChatMessageService chatMessageService;
    private static final AtomicInteger chatNoGenerator = new AtomicInteger(1); // 고유 번호 생성기

    public ChatMessageController(AIService aiService, ChatMessageService chatMessageService) {
        this.aiService = aiService;
        this.chatMessageService = chatMessageService;
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
        message.setSender("USER");
        message.setUserId("user3@example.com");
        message.setRfidId("RFID004"); // # 수정 필요
        message.setType("text");
        message.setReadStatus("Y");

        // DynamoDB에 저장
        chatMessageService.saveMessage(message);

        // AI 응답 생성
        String aiResponse = aiService.getAIResponse(message.getContent());
        ChatMessage aiMessage = new ChatMessage();
        aiMessage.setChatNo((int) (System.currentTimeMillis() / 1000L) + 1);
        aiMessage.setContent(aiResponse);
        aiMessage.setSender("AI");
        aiMessage.setUserId("user3@example.com");
        aiMessage.setRfidId("RFID004"); // # 수정 필요
        aiMessage.setType("text");
        aiMessage.setCreatedAt(LocalDateTime.now().toString());
        aiMessage.setReadStatus("Y");

        // AI 응답 저장
        chatMessageService.saveMessage(aiMessage);

        return aiMessage;
    }
}