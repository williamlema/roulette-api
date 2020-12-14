package com.masiv.roulette.adapter.in.controller;

import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.usecase.service.RouletteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Iterable<RoulettePayload>> list(){

        return ResponseEntity.ok(rouletteService.list());
    }

    @PostMapping("{rouletteId}/open")
    public ResponseEntity<RouletteIdPayload> open(@PathVariable String rouletteId){
        rouletteService.open(rouletteId);

        return ResponseEntity.noContent().build();
    }
}
