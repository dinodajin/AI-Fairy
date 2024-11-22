package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.ChatMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dynamodb")
public class DynamoDBController {

    private final ChatMessageService chatMessageService;

    public DynamoDBController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // DynamoDB 연동 확인
    @GetMapping("/check")
    public String checkDynamoDBConnection() {
        chatMessageService.checkDynamoDBConnection();
        return "DynamoDB connection checked. Check logs for details.";
    }

    // DynamoDB 조회
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMessageService.getAllMessages();
    }

    // DynamoDB 안 읽은 메시지 존재 여부 확인
    @GetMapping("/messages/unread")
    public boolean hasUnreadMessages(@RequestParam String userId, @RequestParam String rfidId) {
        return chatMessageService.hasUnreadMessage(userId, rfidId);
    }

    // DynamoDB 읽음 상태 업데이트
    @PostMapping("/messages/mark-read")
    public String markAllUnreadMessagesAsRead(@RequestParam String userId, @RequestParam String rfidId) {
        return chatMessageService.markAllUnreadMessagesAsRead(userId, rfidId);
    }
}
