package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DynamoDBService {


    private static final AtomicInteger CHAT_NO_GENERATOR = new AtomicInteger(1); // Auto-increment simulation

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbClient dynamoDbClient;

    public DynamoDBService(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbClient dynamoDbClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.dynamoDbClient = dynamoDbClient;
    }

    // DynamoDB 연동 확인
    public void checkDynamoDBConnection() {
        ListTablesResponse response = dynamoDbClient.listTables();
        System.out.println("DynamoDB Tables: " + response.tableNames());
    }

    // DynamoDB 조회
    public List<ChatMessage> getAllMessages() {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));
        return table.scan().items().stream().collect(Collectors.toList());
    }

    public void saveMessage(ChatMessage message) {
        if (message.getChatNo() == 0) {
            message.setChatNo((int) (System.currentTimeMillis() / 1000L)); // 기본값 생성
        }

//        logger.debug("Saving message with CHAT_NO: {}", message.getChatNo());

        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));
        table.putItem(message);
    }

    public int getTodaysMessageCount(String userId, String rfidId, String sender, String today) {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));

        // 오늘 날짜를 기준으로 메시지 수 쿼리
        int count = (int) table.scan().items().stream()
                .filter(message -> message.getUserId().equals(userId) &&
                        message.getRfidId().equals(rfidId) &&
                        message.getSender().equals(sender) &&
                        message.getCreatedAt().startsWith(today)) // 날짜 기준 필터
                .count();

        return count;
    }

    // 읽지 않은 메시지가 존재여부 확인
    public boolean hasUnreadMessage(String userId, String rfidId) {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));

        return table.scan().items().stream()
                .anyMatch(message -> message.getUserId().equals(userId) &&
                        message.getRfidId().equals(rfidId) &&
                        "N".equals(message.getReadStatus()));
    }

    // 모든 읽지 않은 메시지 처리
    public String markAllUnreadMessagesAsRead(String userId, String rfidId) {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));

        long updatedCount = table.scan().items().stream()
                .filter(message -> message.getUserId().equals(userId) &&
                        message.getRfidId().equals(rfidId) &&
                        "N".equals(message.getReadStatus())) // 읽지 않은 메시지 필터링
                .peek(message -> {
                    message.setReadStatus("Y");
                    table.putItem(message); // DynamoDB 업데이트
                })
                .count(); // 처리된 메시지 개수 계산

        if (updatedCount > 0) {
            return "Successfully updated " + updatedCount + " unread message(s) to read status for userId: " + userId + ", rfidId: " + rfidId;
        } else {
            return "No unread messages found for userId: " + userId + ", rfidId: " + rfidId;
        }
    }
}