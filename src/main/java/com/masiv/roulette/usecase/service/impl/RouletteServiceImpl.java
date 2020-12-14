package com.masiv.roulette.usecase.service.impl;

import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.adapter.out.redis.BetRepository;
import com.masiv.roulette.kernel.domain.Bet;
import com.masiv.roulette.usecase.service.mapper.RouletteMapper;
import com.masiv.roulette.adapter.out.redis.RouletteRepository;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.kernel.domain.RouletteStatus;
import com.masiv.roulette.kernel.exception.BadRequestException;
import com.masiv.roulette.kernel.exception.NotFoundException;
import com.masiv.roulette.usecase.service.RouletteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class RouletteServiceImpl implements RouletteService {

    private final RouletteRepository rouletteRepository;
    private final BetRepository betRepository;
    private final String NOT_FOUND_MESSAGE = "Roulette not found";
    private final String BET_BAD_REQUEST_MESSAGE = "Roulette is not open";
    private final String BAD_REQUEST_MESSAGE = "Roulette is already open or is closed";

    @Override
    public RouletteIdPayload create() {
        Roulette newRoulette = rouletteRepository.save(Roulette.builder()
                .id(UUID.randomUUID().toString())
                .status(RouletteStatus.CREATED)
                .build());

        return RouletteIdPayload.builder().rouletteId(newRoulette.getId()).build();
    }

    @Override
    public Iterable<RoulettePayload> list() {

        return StreamSupport.stream(rouletteRepository.findAll().spliterator(), false)
                .map(RouletteMapper::fromRoulette)
                .collect(Collectors.toList());
    }

    @Override
    public void open(String rouletteId) {
        Roulette roulette = rouletteRepository.findById(rouletteId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        if(roulette.isReadyToBeOpen()){
            roulette.setStatus(RouletteStatus.OPEN);
            rouletteRepository.save(roulette);
        } else {
            throw new BadRequestException(BAD_REQUEST_MESSAGE);
        }
    }

    @Override
    public void bet(String rouletteId, String userId, BetRequest request) {
        Roulette roulette = rouletteRepository.findById(rouletteId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        if(roulette.isReadyToReceiveBet()){
            betRepository.save(Bet.builder()
                    .id(UUID.randomUUID().toString())
                    .rouletteId(roulette.getId())
                    .userId(userId)
                    .color(request.getColor())
                    .number(request.getNumber())
                    .value(request.getNumber())
                    .type(request.getType())
                    .build()
            );
        } else {
            throw new BadRequestException(BET_BAD_REQUEST_MESSAGE);
        }
    }
}
