package com.LGDXSCHOOL._dx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

import java.time.LocalDateTime;

public class UserDTO {
    @NotNull
    @JsonProperty("USER_ID")
    private String userId;

    @NotNull
    @JsonProperty("USER_PW")
    private String userPw;

    @NotNull
    @JsonProperty("NAME")
    private String name;

    @NotNull
    @JsonProperty("AGE")
    private Integer age;

    @NotNull
    @JsonProperty("GENDER")
    private String gender;

    @NotNull
    @JsonProperty("PHONE_NUMBER")
    private String phoneNumber;

    @NotNull
    @JsonProperty("CREATED_AT")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}