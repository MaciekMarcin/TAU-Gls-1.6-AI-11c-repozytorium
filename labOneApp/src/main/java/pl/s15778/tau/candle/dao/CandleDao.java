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
    public void save(Candle o) throws IllegalArgumentException {
        if(candles.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does exist");
    
        candles.put(o.getId(),o);
    }

    @Override
    public void update(Candle o) throws IllegalArgumentException {
        if(!candles.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");

        candles.put(1L,o);
    }

    @Override
    public void delete(Candle o) throws IllegalArgumentException {
        if (!candles.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");
            
        candles.remove(o.getId());
    }

}