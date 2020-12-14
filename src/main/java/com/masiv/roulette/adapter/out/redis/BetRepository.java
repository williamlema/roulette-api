package com.masiv.roulette.adapter.out.redis;

import com.masiv.roulette.kernel.domain.Bet;
import org.springframework.data.repository.CrudRepository;

public interface BetRepository extends CrudRepository<Bet, String> {
}
