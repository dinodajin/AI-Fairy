package com.LGDXSCHOOL._dx.repository;

import com.LGDXSCHOOL._dx.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {
    Character findByRfidIdAndUserId(String rfidId, String userId);
}
