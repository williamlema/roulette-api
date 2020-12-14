package com.masiv.roulette.adapter.in.controller.dto;

import com.masiv.roulette.kernel.domain.RouletteStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoulettePayload {
    String id;
    RouletteStatus status;
}
