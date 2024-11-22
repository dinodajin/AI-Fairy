package com.LGDXSCHOOL._dx.service;

import com.LGDXSCHOOL._dx.dto.ItemDTO;
import com.LGDXSCHOOL._dx.entity.Character;
import com.LGDXSCHOOL._dx.entity.Item;
import com.LGDXSCHOOL._dx.repository.CharacterRepository;
import com.LGDXSCHOOL._dx.repository.ItemRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final CharacterRepository characterRepository;
    private final ItemRepository itemRepository;

    public ItemService(CharacterRepository characterRepository, ItemRepository itemRepository) {
        this.characterRepository = characterRepository;
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> getUserItems(String userId, String rfidId, Integer itemIndex) {
        // 1. 유저 정보 확인 (RFID, 타입, 레벨)
        Character character = characterRepository.findByRfidIdAndUserId(rfidId, userId);
        if (character == null) {
            throw new IllegalArgumentException("User or RFID not found.");
        }

        int figureType = character.getFigureType(); // 캐릭터 타입
        int figureLevel = character.getFigureLevel(); // 캐릭터 레벨

        // 2. 아이템 테이블에서 타입과 레벨에 맞는 아이템 검색
        List<Item> items = itemRepository.findItemsByFigureTypeAndLevel(figureType, figureLevel);

        // 3. itemIndex 기반으로 한 번에 하나의 아이템만 응답
        if (itemIndex != null && itemIndex >= 0 && itemIndex < items.size()) {
            Item singleItem = items.get(itemIndex);
            return List.of(new ItemDTO(singleItem.getItemId(), singleItem.getItemName(), singleItem.getDiaryImage()));
        }

        // itemIndex가 없거나 유효하지 않은 경우 전체 리스트 반환
        return items.stream()
                .map(item -> new ItemDTO(item.getItemId(), item.getItemName(), item.getDiaryImage()))
                .collect(Collectors.toList());
    }
}
