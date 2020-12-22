package com.masiv.roulette.adapter.in.controller.dto;

import com.masiv.roulette.kernel.domain.RouletteStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoulettePayload {
    private String id;
    private RouletteStatus status;
}
