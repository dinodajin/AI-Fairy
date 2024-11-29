package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.ModuleDTO;
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

    public void saveModule(ModuleDTO moduleDTO) {
        Module module = new Module();
        module.setModuleId(moduleDTO.getModuleId());
        module.setUserId(moduleDTO.getUserId());
        module.setType(moduleDTO.getType());
        moduleRepository.save(module);
    }

    public String getLastModuleId() {
        String lastId = moduleRepository.findLastModuleId();
        return lastId != null ? lastId : "M_000"; // 기본값 반환
    }
}
