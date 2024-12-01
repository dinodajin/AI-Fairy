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

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getModuleStatus(@RequestParam String moduleId) {
        ModuleConnect moduleConnect = moduleConnectService.getModuleStatus(moduleId);
        Map<String, Object> response = Map.of(
                "moduleId", moduleConnect.getModuleId(),
                "rfidId", moduleConnect.getRfidId()
        );
        return ResponseEntity.ok(response);
    }
}
