package com.LGDXSCHOOL._dx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/json")
public class JsonController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadJson(@RequestBody Map<String, Object> data) {
        try {
            // output1 내용 읽기
            Map<String, Object> output1 = (Map<String, Object>) data.get("output1");
            System.out.println("Output1: " + output1);

            // output2 내용 읽기
            Map<String, Object> output2 = (Map<String, Object>) data.get("output2");
            System.out.println("Output2: " + output2);

            // 필요한 로직 수행 (DB 저장, 파일 저장 등)
            // saveToDatabase(output1, output2);

            return ResponseEntity.ok("JSON files received and processed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error processing JSON files.");
        }
    }
}
