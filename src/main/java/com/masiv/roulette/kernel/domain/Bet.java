package com.masiv.roulette.kernel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("bets")
public class Bet {
    @Id
    private String id;
    private String rouletteId;
    private String userId;
    private BetType type;
    private Integer value;
    private BetColor color;
    private Integer number;

}
