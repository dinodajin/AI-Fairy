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
        // 데이터 저장
        DynamoDbTable<ChatMessage> table = dynamoDbEnhancedClient.table("CHAT_TB", TableSchema.fromBean(ChatMessage.class));
        table.putItem(message);
    }
}

