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
public class ChatMessageService {
    private static final AtomicInteger CHAT_NO_GENERATOR = new AtomicInteger(1); // Auto-increment simulation

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbClient dynamoDbClient;

    public ChatMessageService(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbClient dynamoDbClient) {
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

    // 라즈베리파이로부터 받은 json 채팅 정보 저장
    public void saveMessage(ChatMessage message) {
        if (message.getReadStatus() == null) {
            message.setReadStatus("N"); // 기본값: 읽지 않은 상태
        }

        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));
        table.putItem(message);
    }

    // 기존 db에 라즈베리로부터 받은 output1의 chatno의 존재 여부 확인
    public boolean existsByChatNo(int chatNo) {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));
        ChatMessage message = table.getItem(r -> r.key(k -> k.partitionValue(chatNo)));
        return message != null;
    }


    // 내가 채팅창에서 작성한 text 중 emotionStatus 정보 업데이트
    public void updateEmotionStatus(int chatNo, String emotionStatus) {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));

        ChatMessage existingMessage = table.getItem(r -> r.key(k -> k.partitionValue(chatNo)));
        if (existingMessage != null) {
            existingMessage.setEmotionStatus(emotionStatus);
            table.putItem(existingMessage);
        } else {
            throw new RuntimeException("Message with chatNo " + chatNo + " not found.");
        }
    }

    // 사용자별 메시지 조회
    public List<ChatMessage> getMessagesByUserId(String userId) {
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));
        return table.scan().items().stream()
                .filter(message -> userId.equals(message.getUserId()))
                .collect(Collectors.toList());
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