package com.LGDXSCHOOL._dx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FIGURE_ITEM_DIARY_TB")
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID", nullable = false)
    private int itemId; // PK

    @Column(name = "FIGURE_TYPE", nullable = false)
    private int figureType; // 아이템의 타입 (1, 2, 3 중 하나)

    @Column(name = "FIGURE_LEVEL", nullable = false)
    private int figureLevel; // 하나의 타입에 여러 레벨 존재

    @Column(name = "ITEM_NAME", nullable = false)
    private String itemName; // 아이템 이름

    @Column(name = "DIARY_IMAGE", nullable = true)
    private String diaryImage; // 아이템 이미지 경로

}
