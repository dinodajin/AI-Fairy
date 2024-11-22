package com.LGDXSCHOOL._dx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @JsonProperty("USER_ID")
    private String userId;

    @JsonProperty("RFID_ID")
    private String rfidId;
}
