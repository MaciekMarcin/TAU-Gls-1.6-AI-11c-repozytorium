package pl.s15778.tau.candle.dao;

import pl.s15778.tau.candle.domain.Candle;

import java.util.List;
import java.util.Optional;

public class CandleInMemoryDao implements CandleDao {

    public List<Candle> candles;

    @Override
    public List<Candle> getAll() {
        return candles;
    }

    @Override
    public Optional<Candle> getById(Long id) throws IllegalArgumentException {
        if (candles.stream().noneMatch(candle -> candle.getId().equals(id)))
            throw new IllegalArgumentException("Swieczka z id " + id + " nie istnieje.");
        return candles.stream().filter(candle -> candle.getId().equals(id)).findFirst();
    }


    @Override
    public void save(Candle o) throws IllegalArgumentException {
        if (candles.stream().anyMatch(candle -> candle.getId().equals(o.getId())))
            throw new IllegalArgumentException("Swieczka juz istnieje.");
        candles.add(o);
    }

    @Override
    public void update(Candle o) throws IllegalArgumentException {
        if (candles.stream().noneMatch(candle -> candle.getId().equals(o.getId())))
            throw new IllegalArgumentException("Swieczka nie istnieje.");
        candles.add(o.getId().intValue() - 1, o);
    }

    @Override
    public void delete(Candle o) throws IllegalArgumentException {
        if (candles.stream().noneMatch(candle -> candle.getId().equals(o.getId())))
            throw new IllegalArgumentException("Swieczka nie istnieje.");
        int id = o.getId().intValue() - 1;
        candles.remove(id);
    }
}