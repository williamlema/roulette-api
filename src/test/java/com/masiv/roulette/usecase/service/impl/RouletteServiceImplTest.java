package com.masiv.roulette.usecase.service.impl;

import com.masiv.roulette.adapter.in.controller.dto.BetRequest;
import com.masiv.roulette.adapter.in.controller.dto.RouletteIdPayload;
import com.masiv.roulette.adapter.in.controller.dto.RoulettePayload;
import com.masiv.roulette.adapter.out.redis.RouletteRepository;
import com.masiv.roulette.kernel.domain.Roulette;
import com.masiv.roulette.kernel.domain.RouletteStatus;
import com.masiv.roulette.kernel.exception.BadRequestException;
import com.masiv.roulette.kernel.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.masiv.roulette.util.TestUtil.getRoulette;
import static com.masiv.roulette.util.TestUtil.getRouletteList;
import static com.masiv.roulette.util.TestUtil.getBetRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RouletteServiceImplTest {

    @InjectMocks
    private RouletteServiceImpl rouletteService;

    @Mock
    private RouletteRepository rouletteRepository;

    @Test
    void givenRequestEventWhenCreateRouletteThenReturnRouletteId() {
        Roulette roulette = getRoulette(RouletteStatus.CREATED);
        doReturn(roulette).when(rouletteRepository).save(any(Roulette.class));
        RouletteIdPayload response = rouletteService.create();
        assertEquals(roulette.getId(), response.getRouletteId());
        verify(rouletteRepository).save(any(Roulette.class));
    }

    @Test
    void givenRequestEventWhenListRoulettesThenReturnRoulettePayloadList() {
        List<Roulette> rouletteList = getRouletteList();
        doReturn(rouletteList).when(rouletteRepository).findAll();
        Iterable<RoulettePayload> response = rouletteService.list();
        assertEquals(rouletteList.get(0).getId(), response.iterator().next().getId());
        verify(rouletteRepository).findAll();
    }

    @Test
    void givenRouletteIdWheOpenRouletteThenUpdateRouletteStatus() {
        Roulette roulette = getRoulette(RouletteStatus.CREATED);
        doReturn(Optional.of(roulette)).when(rouletteRepository).findById(roulette.getId());
        doReturn(roulette).when(rouletteRepository).save(any(Roulette.class));
        rouletteService.open(roulette.getId());
        verify(rouletteRepository).findById(roulette.getId());
        verify(rouletteRepository).save(any(Roulette.class));
    }

    @Test
    void givenRouletteIdWheOpenRouletteThenThrowsBadRequestException() {
        Roulette roulette = getRoulette(RouletteStatus.OPEN);
        doReturn(Optional.of(roulette)).when(rouletteRepository).findById(roulette.getId());
        Assertions.assertThrows(BadRequestException.class, () ->
                rouletteService.open(roulette.getId())
        );
    }

    @Test
    void givenRouletteIdWheOpenRouletteThenThrowsNotFoundException() {
        String rouletteId = "fakeId";
        doReturn(Optional.empty()).when(rouletteRepository).findById(rouletteId);
        Assertions.assertThrows(NotFoundException.class, () ->
                rouletteService.open(rouletteId)
        );
    }

    @Test
    void givenRouletteIdAndBetReqeustWheBetThenThrowsNotFoundException() {
        String rouletteId = "fakeId";
        String clientId = "idCliente";
        BetRequest request = getBetRequest();
        doReturn(Optional.empty()).when(rouletteRepository).findById(rouletteId);
        Assertions.assertThrows(NotFoundException.class, () ->
                rouletteService.bet(rouletteId, clientId, request)
        );
    }

    @Test
    void givenRouletteIdAndBetReqeustWheBetThenThrowsBadRequestException() {
        String rouletteId = "fakeId";
        String clientId = "idCliente";
        BetRequest request = getBetRequest();
        Roulette roulette = getRoulette(RouletteStatus.CLOSED);
        doReturn(Optional.of(roulette)).when(rouletteRepository).findById(rouletteId);
        Assertions.assertThrows(BadRequestException.class, () ->
                rouletteService.bet(rouletteId, clientId, request)
        );
    }

    @Test
    void givenRouletteIdWheCloseRouletteThenNotFoundException() {
        String rouletteId = "fakeId";
        doReturn(Optional.empty()).when(rouletteRepository).findById(rouletteId);
        Assertions.assertThrows(NotFoundException.class, () ->
                rouletteService.close(rouletteId)
        );
    }

}