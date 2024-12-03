package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.CharacterDTO;
import com.LGDXSCHOOL._dx.entity.Character;
import com.LGDXSCHOOL._dx.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    // 모든 RFID 및 캐릭터 정보 조회
    public List<Map<String, Object>> getRfidsWithDetails(String userId) {
        List<Character> characters = characterRepository.findAll()
                .stream()
                .filter(character -> character.getUserId().equals(userId))
                .collect(Collectors.toList());

        return characters.stream().map(character -> {
            Map<String, Object> details = new HashMap<>();
            details.put("rfidId", character.getRfidId());
            details.put("gauge", character.getGauge());
            details.put("snackCount", character.getSnackCount());
            details.put("characterType", character.getCharacterType());
            details.put("characterName", character.getCharacterName());
            details.put("characterLevel", character.getCharacterLevel());
            return details;
        }).collect(Collectors.toList());
    }

    // 캐릭터 등록
    public void registerCharacter(CharacterDTO characterRegisterDTO) {
        Character character = new Character();
        character.setRfidId(characterRegisterDTO.getRfidId());
        character.setUserId(characterRegisterDTO.getUserId());
        character.setCharacterType(characterRegisterDTO.getCharacterType());
        character.setCharacterName(characterRegisterDTO.getCharacterName());
        character.setGauge(characterRegisterDTO.getGauge());
        character.setSnackCount(characterRegisterDTO.getSnackCount());
        character.setCharacterLevel(characterRegisterDTO.getCharacterLevel());

        characterRepository.save(character);
    }

    public void updateCharacterDetails(String userId, CharacterDTO characterDTO) {
        Character character = characterRepository.findByUserIdAndRfidId(userId, characterDTO.getRfidId())
                .orElseThrow(() -> new RuntimeException("Character not found for userId: " + userId + " and RFID: " + characterDTO.getRfidId()));

        // Update character details
        character.setGauge(characterDTO.getGauge());
        character.setSnackCount(characterDTO.getSnackCount());
        character.setCharacterLevel(characterDTO.getCharacterLevel());

        // Validation: Gauge should not exceed max value
        if (character.getGauge() == 7) {
            character.setGauge(0);
            character.setCharacterLevel(character.getCharacterLevel() + 1); // 레벨 업
        }

        characterRepository.save(character);
    }

}
