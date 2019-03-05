package pl.s15778.tau.candle.dao;

import pl.s15778.tau.candle.domain.Candle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CandleDao implements Dao<Candle> {
    protected Map<Long, Candle> candles;

    @Override
    public Optional<Candle> get(Long id) {
        return Optional.ofNullable(candles.get(id));
    }

    @Override
    public List<Candle> getAll() {
        return null;
    }

    @Override
    public void save(Candle o) {

    }

    @Override
    public void update(Candle o){
    }

    @Override
    public void delete(Candle o) {
}


}