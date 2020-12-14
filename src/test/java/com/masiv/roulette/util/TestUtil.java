package com.masiv.roulette.util;

import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.kernel.domain.RouletteStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestUtil {

    public static Roulette getRoulette(RouletteStatus status){
        return Roulette.builder()
                .id(UUID.randomUUID().toString())
                .status(status)
                .build();
    }

    public static List<Roulette> getRouletteList(){
        return Arrays.asList(getRoulette(RouletteStatus.CREATED));
    }
}
