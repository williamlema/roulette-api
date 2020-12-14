package com.masiv.roulette.adapter.in.controller.dto;

import com.masiv.roulette.kernel.domain.BetColor;
import com.masiv.roulette.kernel.domain.BetType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Data
@ValidBet
public class BetRequest {

    @NotNull
    private BetType type;
    @Max(10000)
    @Min(1)
    private Integer value;
    private BetColor color;
    @Max(36)
    @Min(0)
    private Integer number;
}
