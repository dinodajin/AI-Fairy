package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.CharacterDTO;
import com.LGDXSCHOOL._dx.entity.Character;
import com.LGDXSCHOOL._dx.repository.CharacterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SnackService {

    private final CharacterRepository characterRepository;
    private final DynamoDBService dynamoDBService;

    public SnackService(CharacterRepository characterRepository, DynamoDBService dynamoDBService) {
        this.characterRepository = characterRepository;
        this.dynamoDBService = dynamoDBService;
    }

    @Transactional
    public String incrementSnackCount(String userId, String rfidId) {
        // 오늘 날짜 가져오기
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // DynamoDB에서 USER_ID, SENDER("USER", "AI") 기준으로 오늘의 채팅 횟수 가져오기
        int userToAiCount = dynamoDBService.getTodaysMessageCount(userId, rfidId, "USER", today);
        int aiToUserCount = dynamoDBService.getTodaysMessageCount(userId, rfidId, "AI", today);

        // 사용자와 AI 간의 총 채팅 횟수 계산
        int totalChatsToday = (userToAiCount + aiToUserCount) / 2;

        // 채팅 횟수가 왕복 3회 이상이면 간식 증가
        if (totalChatsToday >= 3) {
            Character character = characterRepository.findByRfidIdAndUserId(rfidId, userId);
            if (character == null) {
                throw new IllegalArgumentException("Character not found.");
            }

            character.setSnackCount(character.getSnackCount() + 1);
            characterRepository.save(character);

            return "Snack count incremented successfully! Current Snack Count" + character.getSnackCount() + ", Current Gauge: " + character.getGauge() +
                    ", Current Figure Level: " + character.getFigureLevel();
        }
        return "Chat count not sufficient to increment snack count. Current chats today: " + totalChatsToday;
    }

    @Transactional
    public String giveSnack(String userId, String rfidId) {
        System.out.println("GiveSnack called with RFID_ID: " + rfidId + ", USER_ID: " + userId);
        Character character = characterRepository.findByRfidIdAndUserId(rfidId, userId);
        if (character == null) {
            throw new IllegalArgumentException("Character not found for RFID_ID: " + rfidId + " and USER_ID: " + userId);
        }

        if (character.getSnackCount() > 0) {
            character.setSnackCount(character.getSnackCount() - 1);
            character.setGauge(character.getGauge() + 1);

            if (character.getGauge() >= 7) {
                character.setGauge(0);
                character.setFigureLevel(character.getFigureLevel() + 1);
            }

            characterRepository.save(character);
            return "Snack given successfully! Current Snack Count" + character.getSnackCount() + ", Current Gauge: " + character.getGauge() +
                    ", Current Figure Level: " + character.getFigureLevel();
        } else {
            return "No snacks available!";
        }
    }
}
