package com.masiv.roulette.usecase.service.impl;

import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.in.controller.dto.BetResultPayload;
import com.masiv.roulette.adapter.in.controller.dto.CloseRoulettePayload;
import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.adapter.out.redis.BetRepository;
import com.masiv.roulette.kernel.domain.Bet;
import com.masiv.roulette.kernel.domain.BetColor;
import com.masiv.roulette.usecase.service.mapper.RouletteMapper;
import com.masiv.roulette.adapter.out.redis.RouletteRepository;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.kernel.domain.RouletteStatus;
import com.masiv.roulette.kernel.exception.BadRequestException;
import com.masiv.roulette.kernel.exception.NotFoundException;
import com.masiv.roulette.usecase.service.RouletteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.SplittableRandom;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class RouletteServiceImpl implements RouletteService {

    private final RouletteRepository rouletteRepository;
    private final BetRepository betRepository;
    private final String NOT_FOUND_MESSAGE = "Roulette not found";
    private final String BET_BAD_REQUEST_MESSAGE = "Roulette is not open";
    private final String BAD_REQUEST_MESSAGE = "Roulette is already open or is closed";
    private final Integer MIN = 0;
    private final Integer MAX = 36;
    private final Double NUMBER_MULTIPLIER = 5.0;
    private final Double COLOR_MULTIPLIER = 1.8;

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
        if (roulette.isReadyToBeOpen()) {
            roulette.setStatus(RouletteStatus.OPEN);
            rouletteRepository.save(roulette);
        } else {
            throw new BadRequestException(BAD_REQUEST_MESSAGE);
        }
    }

    @Override
    public void bet(String rouletteId, String userId, BetRequest request) {
        Roulette roulette = getOpenRoulette(rouletteId);
        betRepository.save(Bet.builder()
                .id(UUID.randomUUID().toString())
                .rouletteId(roulette.getId())
                .userId(userId)
                .color(request.getColor())
                .number(request.getNumber())
                .value(request.getValue())
                .type(request.getType())
                .build()
        );
    }

    @Override
    public CloseRoulettePayload close(String rouletteId) {
        Roulette roulette = getOpenRoulette(rouletteId);
        Integer winningNumber = new SplittableRandom().nextInt(MIN, MAX);
        Stream<Bet> bets = StreamSupport.stream(betRepository.findAll().spliterator(), false)
                .filter(bet -> roulette.getId().equals(bet.getRouletteId()));
        roulette.setStatus(RouletteStatus.CLOSED);
        rouletteRepository.save(roulette);

        return CloseRoulettePayload.builder()
                .winningNumber(winningNumber)
                .betResults(bets.map(bet -> BetResultPayload.builder()
                        .color(bet.getColor())
                        .number(bet.getNumber())
                        .type(bet.getType())
                        .userId(bet.getUserId())
                        .value(bet.getValue())
                        .result(calculateBetResult(bet, winningNumber))
                        .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private Roulette getOpenRoulette(String rouletteId) {
        Roulette roulette = rouletteRepository.findById(rouletteId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        if (!roulette.isReadyToReceiveBet()) {
            throw new BadRequestException(BET_BAD_REQUEST_MESSAGE);
        }

        return roulette;
    }

    private Double calculateBetResult(Bet bet, Integer winningNumber) {
        double result = 0.0;
        switch (bet.getType()) {
            case NUMBER:
                if (winningNumber.equals(bet.getNumber())) {
                    result = bet.getValue().doubleValue() * NUMBER_MULTIPLIER;
                }
                break;
            case COLOR:
                if ((BetColor.RED.equals(bet.getColor()) && isEvenNumber(winningNumber)) ||
                        (BetColor.BLACK.equals(bet.getColor()) && !isEvenNumber(winningNumber))) {
                    result = bet.getValue().doubleValue() * COLOR_MULTIPLIER;
                }
                break;
        }
        return result;
    }

    private boolean isEvenNumber(Integer number) {
        return number % 2 == 0;
    }
}
