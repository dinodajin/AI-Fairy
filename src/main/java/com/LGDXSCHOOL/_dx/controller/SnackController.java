package com.LGDXSCHOOL._dx.controller;

import com.LGDXSCHOOL._dx.dto.CharacterDTO;
import com.LGDXSCHOOL._dx.service.SnackService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/snack")
public class SnackController {

    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @PostMapping("/increment")
    public String incrementSnackCount(@RequestBody CharacterDTO characterDTO) {
        return snackService.incrementSnackCount(characterDTO.getUserId(), characterDTO.getRfidId());
    }

    @PostMapping("/give")
    public String giveSnack(@RequestBody CharacterDTO characterDTO) {
        return snackService.giveSnack(characterDTO.getUserId(), characterDTO.getRfidId());
    }
}
