package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkModuleForUser(@PathVariable String userId) {
        boolean hasModule = moduleService.hasModule(userId);
        return ResponseEntity.ok(Collections.singletonMap("hasModule", hasModule));
    }
}
