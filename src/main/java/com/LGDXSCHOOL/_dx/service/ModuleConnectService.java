package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.entity.ModuleConnect;
import com.LGDXSCHOOL._dx.repository.ModuleConnectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ModuleConnectService {

    private final ModuleConnectRepository moduleConnectRepository;

    public ModuleConnectService(ModuleConnectRepository moduleConnectRepository) {
        this.moduleConnectRepository = moduleConnectRepository;
    }

    // 상태 업데이트 로직
    public String updateConnectStatus(String moduleId, String rfidId) {
        if (rfidId == null || rfidId.isEmpty()) {
            moduleConnectRepository.updateConnectOnOffByModuleId(moduleId, "N");
            return "N";
        }

        Optional<ModuleConnect> moduleConnect = moduleConnectRepository.findByModuleIdAndRfidId(moduleId, rfidId);

        if (moduleConnect.isPresent()) {
            ModuleConnect connect = moduleConnect.get();
            connect.setConnectOnOff("Y");
            connect.setUpdatedAt(LocalDateTime.now());
            moduleConnectRepository.save(connect);
            return "Y";
        } else {
            throw new RuntimeException("No record found for moduleId: " + moduleId + ", rfidId: " + rfidId);
        }
    }

    // 모듈 상태 조회 로직
    public ModuleConnect getModuleStatus(String moduleId) {
        return moduleConnectRepository.findByModuleId(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found for moduleId: " + moduleId));
    }

    // RFID 기준 연결 상태 조회
    public String getConnectStatusByRfid(String rfidId) {
        return moduleConnectRepository.findByRfidId(rfidId)
                .map(ModuleConnect::getConnectOnOff)
                .orElseThrow(() -> new RuntimeException("RFID not found: " + rfidId));
    }
}
