package com.masiv.roulette.kernel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("roulettes")
public class Roulette {
    @Id
    private String id;
    private RouletteStatus status;

    public boolean isReadyToBeOpen(){
        return RouletteStatus.CREATED.equals(status);
    }
    public boolean isReadyToReceiveBet(){
        return RouletteStatus.OPEN.equals(status);
    }
}
