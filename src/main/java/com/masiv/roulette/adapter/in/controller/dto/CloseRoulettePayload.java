package com.masiv.roulette.adapter.in.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CloseRoulettePayload {
    private Integer winningNumber;
    private List<BetResultPayload> betResults;
}
