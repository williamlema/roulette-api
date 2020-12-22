package com.masiv.roulette.usecase.service.mapper;

import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.adapter.in.controller.dto.BetResultPayload;
import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.kernel.domain.Bet;
import com.masiv.roulette.kernel.domain.Roulette;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RouletteMapper {

    public static RoulettePayload fromRoulette(Roulette roulette) {

        return RoulettePayload.builder()
                .id(roulette.getId())
                .status(roulette.getStatus())
                .build();
    }

    public static Bet fromBetRequest(String userId, BetRequest bet) {

        return Bet.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .color(bet.getColor())
                .number(bet.getNumber())
                .value(bet.getValue())
                .type(bet.getType())
                .build();
    }

    public static List<BetResultPayload> mapBetsToBetResults(List<Bet> bets) {

        return bets.stream()
                .map(RouletteMapper::fromBet)
                .collect(Collectors.toList());
    }

    public static BetResultPayload fromBet(Bet bet) {

        return BetResultPayload.builder()
                .color(bet.getColor())
                .number(bet.getNumber())
                .type(bet.getType())
                .userId(bet.getUserId())
                .value(bet.getValue())
                .result(bet.getResult())
                .build();
    }
}
