package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.ItemDTO;
import com.LGDXSCHOOL._dx.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/user/{userId}/rfid/{rfidId}")
    public List<ItemDTO> getUserItems(@PathVariable String userId,
                                      @PathVariable String rfidId,
                                      @RequestParam(required = false) Integer itemIndex) {
        // itemIndex에 따라 한 번에 하나의 아이템을 반환
        return itemService.getUserItems(userId, rfidId, itemIndex);
    }
}
