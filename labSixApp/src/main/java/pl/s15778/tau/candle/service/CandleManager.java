package pl.s15778.tau.candle.service;

import java.util.List;

import pl.s15778.tau.candle.domain.Candle;

public interface CandleManager {

    Long addCandle(Candle candle);
    void updateCandle(Candle candle);
    Candle findCandleById(Long Id);
    Candle findCandleByFragment(String fragment);
    void deleteCandle(Candle candle);
    List<Candle> findAllCandles();
    List<Candle> findCandles(String nameFragment);
}
