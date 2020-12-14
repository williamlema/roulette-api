package com.masiv.roulette.adapter.in.controller;

import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.usecase.service.RouletteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import javax.validation.Valid;

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
    public ResponseEntity<Void> open(@PathVariable String rouletteId){
        rouletteService.open(rouletteId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("{rouletteId}/bet")
    public ResponseEntity<Void> bet(@PathVariable String rouletteId,
                                    @RequestHeader("userId") String userId,
                                    @Valid @RequestBody BetRequest request){
        rouletteService.bet(rouletteId, userId, request);

        return ResponseEntity.noContent().build();
    }
}
