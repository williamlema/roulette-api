package com.masiv.roulette.adapter.in.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CloseRoulettePayload {
    private Integer winningNumber;
    private List<BetResultPayload> betResults;
}
