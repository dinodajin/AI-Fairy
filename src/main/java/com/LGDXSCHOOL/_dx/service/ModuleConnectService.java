package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.entity.ModuleConnect;
import com.LGDXSCHOOL._dx.repository.ModuleConnectRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleConnectService {

    private final ModuleConnectRepository moduleConnectRepository;

    public ModuleConnectService(ModuleConnectRepository moduleConnectRepository) {
        this.moduleConnectRepository = moduleConnectRepository;
    }

    // 상태 업데이트 로직
    public String updateConnectStatus(String rfidId, boolean isConnected) {
        Optional<ModuleConnect> moduleConnect = moduleConnectRepository.findByRfidId(rfidId);

        if (moduleConnect.isPresent()) {
            ModuleConnect connect = moduleConnect.get();
            connect.setConnectOnOff(isConnected ? "Y" : "N");
            connect.setUpdatedAt(LocalDateTime.now());
            moduleConnectRepository.save(connect);
            return isConnected ? "Y" : "N";
        } else {
            throw new RuntimeException("RFID not found: " + rfidId);
        }
    }

    // RFID 기준 연결 상태 조회
    public String getConnectStatusByRfid(String rfidId) {
        return moduleConnectRepository.findByRfidId(rfidId)
                .map(ModuleConnect::getConnectOnOff)
                .orElseThrow(() -> new RuntimeException("RFID not found: " + rfidId));
    }

    // 모듈 상태 조회
    public ModuleConnect getModuleStatus(String moduleId) {
        return moduleConnectRepository.findByModuleId(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found for moduleId: " + moduleId));
    }

    // 유저 ID를 기준으로 RFID 목록 조회
    public List<String> getRfidsByUserId(String userId) {
        return moduleConnectRepository.findRfidsByUserId(userId);
    }

    // 이전 또는 다음 RFID 조회
    public String getAdjacentRfid(String userId, String currentRfid, String direction) {
        List<String> rfids = moduleConnectRepository.findRfidsByUserId(userId);

        int currentIndex = rfids.indexOf(currentRfid);

        if (currentIndex == -1) {
            throw new RuntimeException("Current RFID not found in user RFIDs.");
        }

        int adjacentIndex = "previous".equals(direction)
                ? currentIndex - 1
                : currentIndex + 1;

        if (adjacentIndex < 0 || adjacentIndex >= rfids.size()) {
            throw new RuntimeException("No adjacent RFID in the specified direction.");
        }

        return rfids.get(adjacentIndex);
    }
}
