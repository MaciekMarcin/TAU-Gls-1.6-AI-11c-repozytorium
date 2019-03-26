package pl.s15778.tau.candle.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pl.s15778.tau.candle.domain.Candle;

public interface CandleDao {
	public Connection getConnection();
	public void setConnection(Connection connection) throws SQLException;
	public List<Candle> getAllCandles();
	public int addCandle(Candle candle) throws SQLException;
	public int deleteCandle(Candle candle) throws SQLException;
	public int updateCandle(Candle candle) throws SQLException;
	public Candle getCandle(long id) throws SQLException;
}