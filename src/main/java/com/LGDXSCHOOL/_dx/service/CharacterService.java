package com.LGDXSCHOOL._dx.service;

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
    private final ModuleConnectService moduleConnectService;

    public CharacterService(CharacterRepository characterRepository, ModuleConnectService moduleConnectService) {
        this.characterRepository = characterRepository;
        this.moduleConnectService = moduleConnectService;
    }

    // 유저의 모든 RFID와 캐릭터 정보 + 모듈 연결 상태 조회
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

            Boolean connectStatus = moduleConnectService.getConnectStatusByRfid(character.getRfidId());
            details.put("connectStatus", connectStatus);

            return details;
        }).collect(Collectors.toList());
    }

    // 특정 RFID의 캐릭터 정보와 모듈 연결 상태 조회
    public Map<String, Object> getCharacterDetails(String userId, String rfidId) {
        Character character = characterRepository.findByRfidIdAndUserId(rfidId, userId);
        if (character == null) {
            throw new RuntimeException("Character not found for RFID: " + rfidId);
        }

        Map<String, Object> details = new HashMap<>();
        details.put("rfidId", character.getRfidId());
        details.put("gauge", character.getGauge());
        details.put("snackCount", character.getSnackCount());
        details.put("characterType", character.getCharacterType());
        details.put("characterName", character.getCharacterName());
        details.put("characterLevel", character.getCharacterLevel());

        Boolean connectStatus = moduleConnectService.getConnectStatusByRfid(rfidId);
        details.put("connectStatus", connectStatus);

        return details;
    }
}
