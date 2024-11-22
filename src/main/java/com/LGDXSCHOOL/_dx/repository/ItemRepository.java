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

    @Query("SELECT i FROM Item i WHERE i.figureType = :figureType AND i.figureLevel <= :figureLevel")
    List<Item> findItemsByFigureTypeAndLevel(@Param("figureType") int figureType,
                                             @Param("figureLevel") int figureLevel);
}


