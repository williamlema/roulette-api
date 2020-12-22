package com.masiv.roulette.usecase.service.util;

import com.masiv.roulette.kernel.domain.Bet;
import com.masiv.roulette.kernel.domain.BetColor;

public class RouletteUtil {

    private static final Double GAIN_NUMBER_MULTIPLIER = 5.0;
    private static final Double GAIN_COLOR_MULTIPLIER = 1.8;

    public static Double calculateBetResult(Bet bet, Integer winningNumber) {
        double result = 0.0;
        switch (bet.getType()) {
            case NUMBER:
                if (winningNumber.equals(bet.getNumber())) {
                    result = bet.getValue() * GAIN_NUMBER_MULTIPLIER;
                }
                break;
            case COLOR:
                if ((BetColor.RED.equals(bet.getColor()) && isEvenNumber(winningNumber)) ||
                        (BetColor.BLACK.equals(bet.getColor()) && !isEvenNumber(winningNumber))) {
                    result = bet.getValue() * GAIN_COLOR_MULTIPLIER;
                }
                break;
        }
        return result;
    }

    public static boolean isEvenNumber(Integer number) {
        return number % 2 == 0;
    }
}
