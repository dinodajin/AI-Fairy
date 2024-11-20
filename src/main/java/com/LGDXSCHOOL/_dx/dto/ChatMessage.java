package com.LGDXSCHOOL._dx.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private String sender;    // 메시지 보낸 사람
    private String content;   // 메시지 내용
    private String type;      // 메시지 타입 ("user" or "bot")
}
