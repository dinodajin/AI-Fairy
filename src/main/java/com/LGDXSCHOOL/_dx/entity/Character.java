package com.LGDXSCHOOL._dx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_CHARACTER_TB")
@Getter @Setter
public class Character {
    @Id
    @Column(name = "RFID_ID", nullable = false)
    private String rfidId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "GAUGE", nullable = false)
    private int gauge;

    @Column(name = "SNACK_COUNT", nullable = false)
    private int snackCount;

    @Column(name = "CHARACTER_TYPE", nullable = false)
    private int characterType;

    @Column(name = "CHARACTER_NAME", nullable = false)
    private String characterName;

    @Column(name = "CHARACTER_LEVEL", nullable = false)
    private int characterLevel;
}

