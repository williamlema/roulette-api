package com.masiv.roulette.adapter.in.controller.dto;

import com.masiv.roulette.kernel.domain.BetColor;
import com.masiv.roulette.kernel.domain.BetType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BetResultPayload {
    private String userId;
    private BetType type;
    private Integer value;
    private BetColor color;
    private Integer number;
    private Double result;
}
