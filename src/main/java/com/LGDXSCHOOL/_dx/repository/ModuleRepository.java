package com.LGDXSCHOOL._dx.repository;

import com.LGDXSCHOOL._dx.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByUserId(String userId); // 사용자 ID로 모듈 목록 조회

    @Query(value = "SELECT m.MODULE_ID FROM MODULE_TB m WHERE ROWNUM = 1 ORDER BY m.MODULE_ID DESC", nativeQuery = true)
    String findLastModuleId();
}
