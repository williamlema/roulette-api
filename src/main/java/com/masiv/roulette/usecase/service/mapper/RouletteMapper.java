package com.masiv.roulette.usecase.service.mapper;

import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.kernel.domain.Roulette;

public class RouletteMapper {

    public static RoulettePayload fromRoulette(Roulette roulette){

        return RoulettePayload.builder()
                .id(roulette.getId())
                .status(roulette.getStatus())
                .build();
    }
}
