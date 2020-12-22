package com.masiv.roulette.util;

import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.kernel.domain.RouletteStatus;
import com.masiv.roulette.kernel.domain.BetColor;
import com.masiv.roulette.kernel.domain.BetType;
import com.masiv.roulette.kernel.domain.Bet;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestUtil {

    public static Roulette getRoulette(RouletteStatus status){

        return Roulette.builder()
                .id(UUID.randomUUID().toString())
                .status(status)
                .bets(Arrays.asList(getBet("testID")))
                .build();
    }

    public static List<Roulette> getRouletteList(){

        return Arrays.asList(getRoulette(RouletteStatus.CREATED));
    }

    public static BetRequest getBetRequest () {

        return BetRequest.builder()
                .color(BetColor.RED)
                .type(BetType.COLOR)
                .value(10000.0)
                .build();
    }

    public static Bet getBet(String userId){

        return Bet.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .color(BetColor.RED)
                .type(BetType.COLOR)
                .value(10000.0)
                .build();
    }

}
