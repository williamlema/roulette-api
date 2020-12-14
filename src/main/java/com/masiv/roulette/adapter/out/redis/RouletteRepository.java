package com.masiv.roulette.adapter.out.redis;

import com.masiv.roulette.kernel.domain.Roulette;
import org.springframework.data.repository.CrudRepository;

public interface RouletteRepository extends CrudRepository<Roulette, String> {
}
