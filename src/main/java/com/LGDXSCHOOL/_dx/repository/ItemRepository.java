package com.LGDXSCHOOL._dx.repository;

import com.LGDXSCHOOL._dx.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.characterType = :characterType AND i.characterLevel <= :characterLevel")
    List<Item> findItemsBycharacterTypeAndLevel(@Param("characterType") int characterType,
                                             @Param("characterLevel") int characterLevel);
}


