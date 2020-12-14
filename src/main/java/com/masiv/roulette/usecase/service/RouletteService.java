package com.masiv.roulette.usecase.service;

import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;

public interface RouletteService {

    RouletteIdPayload create();

    Iterable<RoulettePayload> list();

    void open(String rouletteId);

    void bet(String rouletteId, String userId, BetRequest request);
}
