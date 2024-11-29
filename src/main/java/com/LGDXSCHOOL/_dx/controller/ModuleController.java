package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ModuleDTO;
import com.LGDXSCHOOL._dx.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // 유저가 등록된 모듈 있는지 확인
    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkModuleForUser(@PathVariable String userId) {
        boolean hasModule = moduleService.hasModule(userId);
        return ResponseEntity.ok(Collections.singletonMap("hasModule", hasModule));
    }

    // 유저가 모듈 등록
    @PostMapping("/save")
    public ResponseEntity<?> registerModule(@RequestBody ModuleDTO moduleDTO) {
        System.out.println("MODULE_ID: " + moduleDTO.getModuleId());
        System.out.println("USER_ID: " + moduleDTO.getUserId());
        System.out.println("TYPE: " + moduleDTO.getType());
        try {

            moduleService.saveModule(moduleDTO); // 모듈 저장
            return ResponseEntity.ok("모듈이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모듈 등록 실패: " + e.getMessage());
        }
    }

    @GetMapping("/last-id")
    public ResponseEntity<String> getLastModuleId() {
        try {
            String lastId = moduleService.getLastModuleId();
            return ResponseEntity.ok(lastId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving last MODULE_ID: " + e.getMessage());
        }
    }
}
