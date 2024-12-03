package com.LGDXSCHOOL._dx.repository;

import com.LGDXSCHOOL._dx.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, String> {
    Optional<Character> findByUserIdAndRfidId(String userId, String rfidId);

    Optional<Character> findByRfidId(String rfidId);
}
