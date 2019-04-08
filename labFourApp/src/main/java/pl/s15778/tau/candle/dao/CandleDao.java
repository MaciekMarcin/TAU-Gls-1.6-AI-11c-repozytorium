package pl.s15778.tau.candle.dao;

import java.util.List;
import java.util.Optional;

import pl.s15778.tau.candle.domain.Candle;

public interface CandleDao {

	List<Candle> getAll();
    Optional<Candle> getById(Long id);
    void save(Candle o);
    void update(Candle o);
	void delete(Candle o);

}