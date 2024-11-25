package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.entity.ModuleConnect;
import com.LGDXSCHOOL._dx.service.CharacterService;
import com.LGDXSCHOOL._dx.service.ModuleConnectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/module-connect")
public class ModuleConnectController {

    private final ModuleConnectService moduleConnectService;

    public ModuleConnectController(ModuleConnectService moduleConnectService) {
        this.moduleConnectService = moduleConnectService;
    }

    // RFID 연결 상태 업데이트 API
    @PostMapping("/update-connection")
    public ResponseEntity<Map<String, String>> updateRfidConnection(@RequestBody Map<String, Object> request) {
        try {
            String rfidId = (String) request.get("rfidId");
            boolean isConnected = (boolean) request.get("isConnected");

            String connectStatus = moduleConnectService.updateConnectStatus(rfidId, isConnected);

            Map<String, String> response = Map.of(
                    "rfidId", rfidId,
                    "connectStatus", connectStatus
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getModuleStatus(@RequestParam String moduleId) {
        ModuleConnect moduleConnect = moduleConnectService.getModuleStatus(moduleId);
        Map<String, Object> response = Map.of(
                "moduleId", moduleConnect.getModuleId(),
                "rfidId", moduleConnect.getRfidId(),
                "connectOnOff", moduleConnect.getConnectOnOff()
        );
        return ResponseEntity.ok(response);
    }
}
