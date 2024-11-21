package com.LGDXSCHOOL._dx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_FIGURE_TB")
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

    @Column(name = "FIGURE_TYPE", nullable = false)
    private int figureType;

    @Column(name = "FIGURE_NAME", nullable = false)
    private String figureName;

    @Column(name = "FIGURE_LEVEL", nullable = false)
    private int figureLevel;
}

