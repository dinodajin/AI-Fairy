package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ChatMessage;
import com.LGDXSCHOOL._dx.service.DynamoDBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dynamodb")
public class DynamoDBController {

    private final DynamoDBService dynamoDBService;

    public DynamoDBController(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    // DynamoDB 연동 확인
    @GetMapping("/check")
    public String checkDynamoDBConnection() {
        dynamoDBService.checkDynamoDBConnection();
        return "DynamoDB connection checked. Check logs for details.";
    }

    // DynamoDB 조회
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return dynamoDBService.getAllMessages();
    }
}
