package com.masiv.roulette.usecase.service.impl;

import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.out.redis.RouletteRepository;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.kernel.domain.RouletteStatus;
import com.masiv.roulette.usecase.service.RouletteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class RouletteServiceImpl implements RouletteService {

    private final RouletteRepository rouletteRepository;

    @Override
    public RouletteIdPayload create() {
        Roulette newRoulette = rouletteRepository.save(Roulette.builder()
                .id(UUID.randomUUID().toString())
                .status(RouletteStatus.CREATED)
                .build());

        return RouletteIdPayload.builder().rouletteId(newRoulette.getId()).build();
    }
}
