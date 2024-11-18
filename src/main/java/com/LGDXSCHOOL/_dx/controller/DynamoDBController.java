package com.LGDXSCHOOL._dx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;

@RestController
@RequestMapping("/dynamodb")
public class DynamoDBController {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBController(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @GetMapping("/tables")
    public List<String> listTables() {
        return dynamoDbClient.listTables().tableNames();
    }
}
