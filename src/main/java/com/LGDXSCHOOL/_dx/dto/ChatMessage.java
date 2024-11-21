package com.LGDXSCHOOL._dx.dto;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class ChatMessage {

    private int chatNo; // Primary Key
    private String userId;
    private String rfidId;
    private String createdAt;
    private String content;
    private String emotionStatus;
    private String sender;
    private String type;
    private String readStatus;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("CHAT_NO")
    public int getChatNo() {
        return chatNo;
    }

    public void setChatNo(int chatNo) {
        this.chatNo = chatNo;
    }

    @DynamoDbAttribute("USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbAttribute("RFID_ID")
    public String getRfidId() {
        return rfidId;
    }

    public void setRfidId(String rfidId) {
        this.rfidId = rfidId;
    }

    @DynamoDbAttribute("CREATED_AT")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDbAttribute("CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @DynamoDbAttribute("EMOTION_STATUS")
    public String getEmotionStatus() {
        return emotionStatus;
    }

    public void setEmotionStatus(String emotionStatus) {
        this.emotionStatus = emotionStatus;
    }

    @DynamoDbAttribute("SENDER")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @DynamoDbAttribute("TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDbAttribute("READ_STATUS")
    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
