package com.LGDXSCHOOL._dx.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public String analyzeSentiment(String text) {
        // 간단한 감성 분석 로직
        if (text.contains("좋아") || text.contains("행복")) {
            return "positive";
        } else if (text.contains("싫어") || text.contains("슬퍼")) {
            return "negative";
        }
        return "neutral";
    }
}

