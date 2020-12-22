package com.masiv.roulette.kernel.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.masiv.roulette.usecase.service.util.RouletteUtil.calculateBetResult;

@JsonSerialize(as = Bet.BetBuilder.class)
@JsonDeserialize(as = Bet.BetBuilder.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bet implements Serializable {
    private String id;
    private String userId;
    private BetType type;
    private Double value;
    private BetColor color;
    private Integer number;
    private Double result;

    public void calculateResult (Integer winningNumber) {
        this.result = calculateBetResult(this, winningNumber);
    }

}
