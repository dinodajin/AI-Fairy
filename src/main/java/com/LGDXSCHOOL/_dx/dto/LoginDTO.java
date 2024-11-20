package com.LGDXSCHOOL._dx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 로그인 요청 DTO
@Data
public class LoginDTO {
    @JsonProperty("USER_ID")
    private String userId;

    @JsonProperty("USER_PW")
    private String userPw;


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
}