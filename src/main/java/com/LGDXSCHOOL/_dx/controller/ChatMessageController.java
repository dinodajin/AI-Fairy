package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageController.class);

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final RestTemplate restTemplate;

    public ChatMessageController(ChatMessageService chatMessageService, SimpMessagingTemplate messagingTemplate, RestTemplate restTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
        this.restTemplate = restTemplate;
    }

    // 사용자 메시지 전송 및 DynamoDB 저장
    @PostMapping("/send")
    public ResponseEntity<String> sendMessageToRaspberryPi(@RequestBody ChatMessage message) {
        try {
            // chatNo 생성 후 DynamoDB 저장
            message.setChatNo((int) (System.currentTimeMillis() / 1000L));
            chatMessageService.saveMessage(message);

            // 라즈베리파이로 chatNo와 content만 전송
            Map<String, Object> payload = Map.of(
                    "chatNo", message.getChatNo(),
                    "content", message.getContent()
            );

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://192.168.0.120:5000/processMessage",
                    payload,
                    String.class
            );

            logger.info("Message sent to Raspberry Pi: {}", payload);
            return ResponseEntity.ok("Message sent successfully: " + response.getBody());
        } catch (Exception e) {
            logger.error("Failed to send message to Raspberry Pi", e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 라즈베리파이에서 JSON 수신 및 처리
    @PostMapping("/receive")
    public ResponseEntity<String> receiveJsonFromRaspberryPi(@RequestBody Map<String, Object> data) {
        try {
            // 사용자 메시지 (output1)
            Map<String, Object> output1 = (Map<String, Object>) data.get("output1");

            if (output1.containsKey("CHAT_NO")) {
                // chatNo가 있는 경우
                int chatNo = (int) output1.get("CHAT_NO");
                String emotionStatus = (String) output1.get("EMOTION_STATUS");

                // output1의 emotionStatus만 업데이트
                chatMessageService.updateEmotionStatus(chatNo, emotionStatus);
                logger.info("Updated emotionStatus for chatNo {}: {}", chatNo, emotionStatus);

                // AI 응답 메시지 (output2) 처리
                Map<String, Object> output2 = (Map<String, Object>) data.get("output2");
                ChatMessage aiMessage = convertToChatMessage(output2, "AI");
                chatMessageService.saveMessage(aiMessage);

                // WebSocket 브로드캐스트
                messagingTemplate.convertAndSend("/topic/messages", aiMessage);
                logger.info("Saved and broadcasted AI message: {}", aiMessage);

            } else {
                // chatNo가 없는 경우 -> 새로운 메시지 저장
                ChatMessage userMessage = convertToChatMessage(output1, "USER");
                chatMessageService.saveMessage(userMessage);

                Map<String, Object> output2 = (Map<String, Object>) data.get("output2");
                ChatMessage aiMessage = convertToChatMessage(output2, "AI");
                chatMessageService.saveMessage(aiMessage);

                // WebSocket 브로드캐스트
                messagingTemplate.convertAndSend("/topic/messages", userMessage);
                messagingTemplate.convertAndSend("/topic/messages", aiMessage);
                logger.info("Saved and broadcasted new messages: {} and {}", userMessage, aiMessage);
            }

            return ResponseEntity.ok("Messages processed successfully.");
        } catch (Exception e) {
            logger.error("Error processing JSON data from Raspberry Pi", e);
            return ResponseEntity.badRequest().body("Error processing JSON data: " + e.getMessage());
        }
    }

    // 모든 메시지 조회
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMessageService.getAllMessages();
    }

    // 특정 사용자의 메시지 조회
    @GetMapping("/messages/user")
    public List<ChatMessage> getUserMessages(@RequestParam String userId) {
        return chatMessageService.getMessagesByUserId(userId);
    }

    // 안 읽은 메시지 존재 여부 확인
    @GetMapping("/messages/unread")
    public boolean hasUnreadMessages(@RequestParam String userId, @RequestParam String rfidId) {
        return chatMessageService.hasUnreadMessage(userId, rfidId);
    }

    // 안 읽은 메시지 읽음 처리
    @PostMapping("/messages/mark-read")
    public String markAllUnreadMessagesAsRead(@RequestParam String userId, @RequestParam String rfidId) {
        return chatMessageService.markAllUnreadMessagesAsRead(userId, rfidId);
    }

    // 검색 기능 추가
    @GetMapping("/search")
    public ResponseEntity<List<ChatMessage>> searchMessages(@RequestParam String keyword) {
        try {
            List<ChatMessage> searchResults = chatMessageService.searchMessagesByKeyword(keyword);
            if (searchResults.isEmpty()) {
                return ResponseEntity.noContent().build(); // 검색 결과가 없을 때
            }
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            logger.error("Error while searching messages with keyword: {}", keyword, e);
            return ResponseEntity.status(500).body(null);
        }
    }


    // JSON 데이터를 ChatMessage로 변환
    private ChatMessage convertToChatMessage(Map<String, Object> data, String sender) {
        ChatMessage message = new ChatMessage();
        message.setChatNo(data.containsKey("CHAT_NO") ? (int) data.get("CHAT_NO") : (int) (System.currentTimeMillis() / 1000L));
        message.setContent((String) data.get("CONTENT"));
        message.setCreatedAt((String) data.get("CREATED_AT"));
        message.setEmotionStatus((String) data.getOrDefault("EMOTION_STATUS", "Neutral"));
        message.setSender(sender);
        message.setRfidId((String) data.getOrDefault("RFID_ID", "Unknown"));
        message.setType("text");
        message.setReadStatus("N");
        return message;
    }
}
