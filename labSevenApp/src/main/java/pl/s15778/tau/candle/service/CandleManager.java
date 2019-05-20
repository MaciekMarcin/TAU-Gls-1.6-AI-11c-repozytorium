package pl.s15778.tau.candle.service;

import java.util.List;

import pl.s15778.tau.candle.domain.CandleStick;
import pl.s15778.tau.candle.domain.Candle;

public interface CandleManager {

    Long addCandleStick(CandleStick candleStick);
    void updateCandleStick(CandleStick candleStick);
    CandleStick findCandleStickById(Long Id);
    CandleStick findCandleStickByFragment(String fragmentStick);
    void deleteCandleStick(CandleStick candleStick);
    List<CandleStick> findAllCandleStick();
    List<CandleStick> findCandleStick(String nameStickFragment);

    Long addCandle(Candle candle);
    void updateCandle(Candle candle);
    Candle findCandleById(Long Id);
    void deleteCandle(Candle candle);
    List<Candle> findAllCandles();
    List<Candle> getCandlesOfCandleStick(Long id);
    void moveCandles(CandleStick candleStick1, CandleStick candleStick2, Candle candle);
}
