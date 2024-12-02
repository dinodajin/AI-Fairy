package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.CharacterDTO;
import com.LGDXSCHOOL._dx.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 특정 RFID의 캐릭터 정보 조회
    @GetMapping("/rfid-detail")
    public ResponseEntity<CharacterDTO> getCharacterDetails(@RequestParam String rfidId) {
        try {
            CharacterDTO characterDetails = characterService.getCharacterDetails(rfidId);
            return ResponseEntity.ok(characterDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 캐릭터 등록
    @PostMapping("/register")
    public ResponseEntity<String> registerCharacter(@RequestBody CharacterDTO characterRegisterDTO) {
        try {
            characterService.registerCharacter(characterRegisterDTO);
            return ResponseEntity.ok("캐릭터가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("캐릭터 등록 중 오류 발생: " + e.getMessage());
        }
    }
}
