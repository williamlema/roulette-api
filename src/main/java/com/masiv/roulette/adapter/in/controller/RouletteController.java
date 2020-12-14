package com.masiv.roulette.adapter.in.controller;

import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.usecase.service.RouletteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RouletteController {

    private final RouletteService rouletteService;

    @PostMapping
    public ResponseEntity<RouletteIdPayload> create(){
        return ResponseEntity.ok(rouletteService.create());
    }

    @GetMapping
    public ResponseEntity<Iterable<Roulette>> list(){
        return ResponseEntity.ok(rouletteService.list());
    }
}
