package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // 유저가 가진 모든 RFID와 캐릭터 정보 조회
    @GetMapping("/user-rfids")
    public ResponseEntity<List<Map<String, Object>>> getUserRfidsWithDetails(@RequestParam String userId) {
        try {
            List<Map<String, Object>> rfidsWithDetails = characterService.getRfidsWithDetails(userId);
            return ResponseEntity.ok(rfidsWithDetails);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // 특정 RFID의 캐릭터 정보 조회
    @GetMapping("/rfid-detail")
    public ResponseEntity<Map<String, Object>> getRfidDetails(
            @RequestParam String userId,
            @RequestParam String rfidId) {
        try {
            Map<String, Object> rfidDetails = characterService.getCharacterDetails(userId, rfidId);
            return ResponseEntity.ok(rfidDetails);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
