package com.LGDXSCHOOL._dx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterDTO {
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

    public String getRfidId() {
        return rfidId;
    }

    public void setRfidId(String rfidId) {
        this.rfidId = rfidId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGauge() {
        return gauge;
    }

    public void setGauge(int gauge) {
        this.gauge = gauge;
    }

    public int getSnackCount() {
        return snackCount;
    }

    public void setSnackCount(int snackCount) {
        this.snackCount = snackCount;
    }

    public int getCharacterType() {
        return characterType;
    }

    public void setCharacterType(int characterType) {
        this.characterType = characterType;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }
}
