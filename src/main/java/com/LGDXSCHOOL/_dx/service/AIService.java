package com.LGDXSCHOOL._dx.service;

import org.springframework.stereotype.Service;

@Service
public class AIService {

    public String getAIResponse(String userMessage) {
        // #수정: 예제 응답임 -> OpenAI API 같은 것을 호출할 예정
        if (userMessage.contains("안녕")) {
            return "안녕하세요! 어떻게 도와드릴까요?";
        }
        return "죄송해요, 잘 이해하지 못했어요.";
    }
}
