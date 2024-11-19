package com.LGDXSCHOOL._dx.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_TB")  // 테이블 이름과 매핑
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USER_PW", nullable = false)
    private String userPw;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @Column(name = "GENDER", nullable = false)
    private String gender;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT")
    private String updatedAt;
}