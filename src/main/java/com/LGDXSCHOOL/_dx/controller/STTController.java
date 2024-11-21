package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.dto.STTRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/stt")
public class STTController {

    private final SimpMessagingTemplate messagingTemplate;

    public STTController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/receive")
    public String receiveSTT(@RequestBody STTRequest sttRequest) {
        ChatMessage chatMessage = new ChatMessage();

        // 서버에서 CHAT_NO 자동 생성
        chatMessage.setChatNo((int) (System.currentTimeMillis() / 1000L));

        // STT에서 받은 내용을 USER_CONTENT로 저장
        chatMessage.setContent(sttRequest.getContent());
        chatMessage.setSender("ME"); // 발신자 설정
        chatMessage.setType("text"); // 메시지 유형
        chatMessage.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // 현재 시간 추가
        chatMessage.setReadStatus("N"); // 기본값으로 읽음 상태를 N으로 설정

        System.out.println(chatMessage);

        // WebSocket을 통해 클라이언트에 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages", chatMessage);

        return "STT 메시지 수신 완료";
    }
}
