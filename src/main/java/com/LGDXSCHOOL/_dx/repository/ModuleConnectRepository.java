package com.LGDXSCHOOL._dx.repository;

import com.LGDXSCHOOL._dx.entity.ModuleConnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleConnectRepository extends JpaRepository<ModuleConnect, Long> {


    Optional<ModuleConnect> findByModuleId(String moduleId);

    // RFID 기준으로 상세 정보 조회
    Optional<ModuleConnect> findByRfidId(String rfidId);

    // 유저 ID를 기준으로 RFID 목록 조회
    @Query("SELECT m.rfidId FROM ModuleConnect m WHERE m.moduleId = :userId  ORDER BY m.createdAt ASC")
    List<String> findRfidsByUserId(String userId);
}
