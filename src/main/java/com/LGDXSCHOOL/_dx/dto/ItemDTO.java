package com.LGDXSCHOOL._dx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDTO {
    @JsonProperty("ITEM_ID")
    private int itemId;

    @JsonProperty("ITEM_NAME")
    private String itemName;

    @JsonProperty("DIARY_IMAGE")
    private String diaryImage;

    public ItemDTO(int itemId, String itemName, String diaryImage) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.diaryImage = diaryImage;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDiaryImage() {
        return diaryImage;
    }

    public void setDiaryImage(String diaryImage) {
        this.diaryImage = diaryImage;
    }
}
