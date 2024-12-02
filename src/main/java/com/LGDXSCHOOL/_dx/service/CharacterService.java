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

    // 특정 RFID로 캐릭터 조회
    public CharacterDTO getCharacterDetails(String rfidId) {
        Character character = characterRepository.findByRfidId(rfidId)
                .orElseThrow(() -> new RuntimeException("Character not found for RFID: " + rfidId));

        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setNo(character.getNo());
        characterDTO.setRfidId(character.getRfidId());
        characterDTO.setUserId(character.getUserId());
        characterDTO.setCharacterType(character.getCharacterType());
        characterDTO.setCharacterName(character.getCharacterName());
        characterDTO.setGauge(character.getGauge());
        characterDTO.setSnackCount(character.getSnackCount());
        characterDTO.setCharacterLevel(character.getCharacterLevel());

        return characterDTO;
    }
}
