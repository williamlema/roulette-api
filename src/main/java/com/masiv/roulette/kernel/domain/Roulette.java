package com.masiv.roulette.kernel.domain;

import com.masiv.roulette.kernel.exception.BadRequestException;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.masiv.roulette.kernel.constant.MessageConstants.BAD_REQUEST_MESSAGE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("roulettes")
public class Roulette {
    @Id
    private String id;
    private RouletteStatus status;
    private List<Bet> bets = new ArrayList<>();

    public void open() {
        if (RouletteStatus.CREATED.equals(status)) {
            this.status = RouletteStatus.OPEN;
        } else {
            throw new BadRequestException(BAD_REQUEST_MESSAGE);
        }
    }

    public void bet(Optional<Bet> optionalBet) {
        optionalBet.ifPresent(b -> bets.add(b));
    }

    public void close(Integer winningNumber){
        this.status = RouletteStatus.CLOSED;
        bets.forEach(bet -> bet.calculateResult(winningNumber));
    }

    public boolean isReadyToReceiveBet() {

        return RouletteStatus.OPEN.equals(status);
    }
}
