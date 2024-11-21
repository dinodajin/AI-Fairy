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

    @JsonProperty("FIGURE_TYPE")
    private int figureType;

    @JsonProperty("FIGURE_NAME")
    private String figureName;

    @JsonProperty("FIGURE_LEVEL")
    private int figureLevel;

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

    public int getFigureType() {
        return figureType;
    }

    public void setFigureType(int figureType) {
        this.figureType = figureType;
    }

    public String getFigureName() {
        return figureName;
    }

    public void setFigureName(String figureName) {
        this.figureName = figureName;
    }

    public int getFigureLevel() {
        return figureLevel;
    }

    public void setFigureLevel(int figureLevel) {
        this.figureLevel = figureLevel;
    }
}
