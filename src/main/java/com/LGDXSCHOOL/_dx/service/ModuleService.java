package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.repository.ModuleRepository;
import com.LGDXSCHOOL._dx.entity.Module;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public boolean hasModule(String userId) {
        // 사용자 ID로 모듈 조회
        List<Module> modules = moduleRepository.findByUserId(userId);
        // 데이터가 비어 있지 않으면 true 반환
        return !modules.isEmpty();
    }
}
