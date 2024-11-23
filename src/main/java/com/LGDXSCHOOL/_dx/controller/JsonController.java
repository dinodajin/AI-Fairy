package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/json")
public class JsonController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public JsonController(ChatMessageService chatMessageService, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadJson(@RequestBody Map<String, Object> data) {
        try {
            // 사용자 메시지 (output1)
            Map<String, Object> output1 = (Map<String, Object>) data.get("output1");
            ChatMessage userMessage = convertToChatMessage(output1, "USER");

            // AI 응답 메시지 (output2)
            Map<String, Object> output2 = (Map<String, Object>) data.get("output2");
            ChatMessage aiMessage = convertToChatMessage(output2, "AI");

            // DynamoDB 저장
            chatMessageService.saveMessage(userMessage);
            chatMessageService.saveMessage(aiMessage);

            // WebSocket으로 브로드캐스트
            messagingTemplate.convertAndSend("/topic/messages", userMessage);
            messagingTemplate.convertAndSend("/topic/messages", aiMessage);

            return ResponseEntity.ok("Messages saved and broadcasted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error processing JSON files.");
        }
    }

    private ChatMessage convertToChatMessage(Map<String, Object> data, String sender) {
        ChatMessage message = new ChatMessage();
        message.setChatNo((int) (System.currentTimeMillis() / 1000L)); // 고유 번호
        message.setContent((String) data.get("CONTENT"));
        message.setCreatedAt((String) data.get("CREATED_AT"));
        message.setEmotionStatus((String) data.getOrDefault("EMOTION_STATUS", "Neutral"));
        message.setSender(sender);
        message.setRfidId((String) data.getOrDefault("RFID_ID", "Unknown"));
        message.setType("text"); // 기본 메시지 유형
        message.setReadStatus("N"); // 기본값: 읽지 않은 상태
        return message;
    }
}
