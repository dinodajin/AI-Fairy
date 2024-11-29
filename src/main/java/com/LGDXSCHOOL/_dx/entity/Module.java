package com.LGDXSCHOOL._dx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MODULE_TB")
@Getter @Setter
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MODULE_ID")
    private Long moduleId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "TYPE", nullable = false)
    private String type;

}
