package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.CharacterDTO;
import com.LGDXSCHOOL._dx.entity.Character;
import com.LGDXSCHOOL._dx.repository.CharacterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SnackService {

    private final CharacterRepository characterRepository;

    public SnackService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Transactional
    public String incrementSnackCount(String userId, String rfidId) {
        System.out.println("IncrementSnackCount called with RFID_ID: " + rfidId + ", USER_ID: " + userId);
        Character character = characterRepository.findByRfidIdAndUserId(rfidId, userId);
        if (character == null) {
            throw new IllegalArgumentException("Character not found for RFID_ID: " + rfidId + " and USER_ID: " + userId);
        }
        character.setSnackCount(character.getSnackCount() + 1);
        characterRepository.save(character);
        return "Snack increase successfully! Current Snack Count" + character.getSnackCount() + ", Current Gauge: " + character.getGauge() +
                ", Current Figure Level: " + character.getFigureLevel();
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
