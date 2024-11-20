package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat") // 클라이언트에서 /app/chat으로 메시지 전송
    @SendTo("/topic/messages") // 클라이언트에게 /topic/messages로 브로드캐스트
    public ChatMessage handleMessage(ChatMessage message) {
        System.out.println("Received message: " + message.getContent());
        ChatMessage response = new ChatMessage();
        response.setContent("서버 응답: " + message.getContent());
        return response; // JSON 형태로 반환
    }
}

