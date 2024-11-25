package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.entity.ModuleConnect;
import com.LGDXSCHOOL._dx.repository.ModuleConnectRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.Map;

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

    // 유저 ID를 기준으로 RFID 목록 조회
    public List<String> getRfidsByUserId(String userId) {
        return moduleConnectRepository.findRfidsByUserId(userId);
    }

    // RFID 상세 정보 조회
    public Map<String, String> getRfidDetails(String rfidId) {
        ModuleConnect moduleConnect = moduleConnectRepository.findByRfidId(rfidId)
                .orElseThrow(() -> new RuntimeException("RFID not found: " + rfidId));

        Map<String, String> details = new HashMap<>();
        details.put("moduleId", moduleConnect.getModuleId());
        details.put("rfidId", moduleConnect.getRfidId());
        details.put("connectOnOff", moduleConnect.getConnectOnOff());
        details.put("updatedAt", moduleConnect.getUpdatedAt().toString());

        return details;
    }
}
