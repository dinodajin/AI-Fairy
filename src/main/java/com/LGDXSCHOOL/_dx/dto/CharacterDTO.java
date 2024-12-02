package com.LGDXSCHOOL._dx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterDTO {
    @JsonProperty("NO")
    private Long no;

    @JsonProperty("RFID_ID")
    private String rfidId;

    @JsonProperty("USER_ID")
    private String userId;

    @JsonProperty("GAUGE")
    private int gauge;

    @JsonProperty("SNACK_COUNT")
    private int snackCount;

    @JsonProperty("CHARACTER_TYPE")
    private int characterType;

    @JsonProperty("CHARACTER_NAME")
    private String characterName;

    @JsonProperty("CHARACTER_LEVEL")
    private int characterLevel;
}
