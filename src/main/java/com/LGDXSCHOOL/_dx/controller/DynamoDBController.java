package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dynamodb")
public class DynamoDBController {

    private final ChatMessageService chatMessageService;

    public DynamoDBController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
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
}
