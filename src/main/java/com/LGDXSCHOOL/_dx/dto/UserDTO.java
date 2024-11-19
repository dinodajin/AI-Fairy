package com.LGDXSCHOOL._dx.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotNull
    private String USER_ID;

    @NotNull
    private String USER_PW;

    @NotNull
    private String NAME;

    @NotNull
    private Integer AGE;

    @NotNull
    private String GENDER;

    @NotNull
    private String PHONE_NUMBER;

    @NotNull
    private String CREATED_AT;

    private String UPDATED_AT;


    // Getters and Setters
    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_PW() {
        return USER_PW;
    }

    public void setUSER_PW(String USER_PW) {
        this.USER_PW = USER_PW;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Integer getAGE() {
        return AGE;
    }

    public void setAGE(Integer AGE) {
        this.AGE = AGE;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public String getCREATED_AT() {
        return CREATED_AT;
    }

    public void setCREATED_AT(String CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }

    public String getUPDATED_AT() {
        return UPDATED_AT;
    }

    public void setUPDATED_AT(String UPDATED_AT) {
        this.UPDATED_AT = UPDATED_AT;
    }
}



