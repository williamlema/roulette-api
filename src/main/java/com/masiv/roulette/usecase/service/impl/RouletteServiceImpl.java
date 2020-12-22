package com.masiv.roulette.usecase.service.impl;

import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.in.controller.dto.CloseRoulettePayload;
import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.kernel.domain.Bet;
import com.masiv.roulette.usecase.service.mapper.RouletteMapper;
import com.masiv.roulette.adapter.out.redis.RouletteRepository;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.kernel.domain.RouletteStatus;
import com.masiv.roulette.kernel.exception.BadRequestException;
import com.masiv.roulette.kernel.exception.NotFoundException;
import com.masiv.roulette.usecase.service.RouletteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.masiv.roulette.kernel.constant.MessageConstants.BET_BAD_REQUEST_MESSAGE;
import static com.masiv.roulette.kernel.constant.MessageConstants.NOT_FOUND_MESSAGE;
import static com.masiv.roulette.usecase.service.mapper.RouletteMapper.fromBetRequest;

@Service
public class RouletteServiceImpl implements RouletteService {

    private final RouletteRepository rouletteRepository;

    @Value("${roulette.limits.min}")
    private Integer min;
    @Value("${roulette.limits.max}")
    private Integer max;

    public RouletteServiceImpl(RouletteRepository rouletteRepository) {
        this.rouletteRepository = rouletteRepository;
    }

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
        roulette.open();
        rouletteRepository.save(roulette);
    }

    @Override
    public void bet(String rouletteId, String userId, BetRequest request) {
        Roulette roulette = getOpenRoulette(rouletteId);
        Optional<Bet> optionalBet = Optional.ofNullable(request).map(bet -> fromBetRequest(userId, bet));
        roulette.bet(optionalBet);
        rouletteRepository.save(roulette);
    }

    @Override
    public CloseRoulettePayload close(String rouletteId) {
        Roulette roulette = getOpenRoulette(rouletteId);
        Integer winningNumber = new SplittableRandom().nextInt(min, max);
        roulette.close(winningNumber);
        rouletteRepository.save(roulette);

        return CloseRoulettePayload.builder()
                .winningNumber(winningNumber)
                .betResults(RouletteMapper.mapBetsToBetResults(roulette.getBets()))
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
}
