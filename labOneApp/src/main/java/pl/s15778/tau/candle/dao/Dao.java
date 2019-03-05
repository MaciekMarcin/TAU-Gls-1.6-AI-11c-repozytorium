package pl.s15778.tau.candle.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<P> {
    public Optional<P> get(Long id);
    public List<P> getAll();
    public void save(P o);
    public void update(P o) throws IllegalArgumentException;
    public void delete(P o);
}