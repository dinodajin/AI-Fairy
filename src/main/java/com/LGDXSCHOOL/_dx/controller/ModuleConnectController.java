package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.entity.ModuleConnect;
import com.LGDXSCHOOL._dx.service.CharacterService;
import com.LGDXSCHOOL._dx.service.ModuleConnectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/character")
public class ModuleConnectController {

    private final CharacterService characterService;
    private final ModuleConnectService moduleConnectService;

    public ModuleConnectController(CharacterService characterService, ModuleConnectService moduleConnectService) {
        this.characterService = characterService;
        this.moduleConnectService = moduleConnectService;
    }

    // 라즈베리파이에서 RFID 받아오는  API
    // 기존 상태 업데이트 API
    @PostMapping("/update-status")
    public ResponseEntity<Map<String, String>> updateModuleStatus(@RequestBody Map<String, Object> request) {
        String rfidId = request.get("rfidId") != null ? request.get("rfidId").toString() : null;
        String connectStatus = moduleConnectService.updateConnectStatus("1", rfidId);

        Map<String, String> response = new HashMap<>();
        response.put("moduleId", "1");
        response.put("rfidId", rfidId == null ? "null" : rfidId);
        response.put("status", connectStatus);

        return ResponseEntity.ok(response);
    }

    // 모듈 상태 조회 API (모듈 ID 기준)
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getModuleStatus(@RequestParam String moduleId) {
        try {
            ModuleConnect moduleConnect = moduleConnectService.getModuleStatus(moduleId);
            Map<String, Object> response = new HashMap<>();
            response.put("moduleId", moduleConnect.getModuleId());
            response.put("rfidId", moduleConnect.getRfidId());
            response.put("connectOnOff", moduleConnect.getConnectOnOff());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", "Module not found"));
        }
    }

    // Flutter에서 클릭한 RFID 정보를 반환하는 API
    // RFID 기준으로 연결 상태 조회 API
    @GetMapping("/rfid-status")
    public ResponseEntity<Map<String, String>> getConnectStatusByRfid(@RequestParam String rfidId) {
        try {
            String connectStatus = moduleConnectService.getConnectStatusByRfid(rfidId);
            Map<String, String> response = Map.of(
                    "rfidId", rfidId,
                    "status", connectStatus
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "RFID not found: " + rfidId
            ));
        }
    }

    // 이전 또는 다음 RFID 조회
    @GetMapping("/adjacent-rfid")
    public ResponseEntity<String> getAdjacentRfid(
            @RequestParam String userId,
            @RequestParam String currentRfid,
            @RequestParam String direction) {
        try {
            String adjacentRfid = moduleConnectService.getAdjacentRfid(userId, currentRfid, direction);
            return ResponseEntity.ok(adjacentRfid);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}