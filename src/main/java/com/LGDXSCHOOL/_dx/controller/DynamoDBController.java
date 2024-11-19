package com.LGDXSCHOOL._dx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.util.List;

@RestController
public class DynamoDBController {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBController(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @GetMapping("/dynamodb/tables")
    public List<String> listTables() {
        ListTablesResponse response = dynamoDbClient.listTables();
        return response.tableNames();
    }
}
