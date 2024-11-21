package com.LGDXSCHOOL._dx.service;

import org.springframework.stereotype.Service;

@Service
public class AIService {

    public String getAIResponse(String userMessage) {
        // #수정: 예제 응답임 -> OpenAI API 같은 것을 호출할 예정
        if (userMessage.contains("안녕")) {
            return "안녕하세요! 저는 AI입니다. 무엇을 도와드릴까요?";
        } else if (userMessage.contains("이름")) {
            return "저는 AI Assistant입니다!";
        } else if (userMessage.contains("날씨")) {
            return "오늘의 날씨는 맑고 화창합니다!";
        } else if (userMessage.contains("도움")) {
            return "무엇이든 물어보세요. 최선을 다해 도와드리겠습니다.";
        }
        return "죄송해요, 잘 이해하지 못했어요.";
    }
}
