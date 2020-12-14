package com.masiv.roulette.usecase.service;

import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.kernel.domain.Roulette;

public interface RouletteService {

    RouletteIdPayload create();

    Iterable<Roulette> list();
}
