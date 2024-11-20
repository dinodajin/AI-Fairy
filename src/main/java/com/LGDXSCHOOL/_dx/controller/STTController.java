package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.dto.STTRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

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
        chatMessage.setSender("user");
        chatMessage.setContent(sttRequest.getContent());
        chatMessage.setType("user");
        System.out.println(chatMessage);

        // WebSocket을 통해 클라이언트에 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages", chatMessage);

        return "STT 메시지 수신 완료";
    }
}
