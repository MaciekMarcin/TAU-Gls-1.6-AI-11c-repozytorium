package pl.s15778.tau.candle.dao;

import pl.s15778.tau.candle.dao.CandleDao;
import pl.s15778.tau.candle.domain.Candle;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CandleDaoJdbcImpl implements CandleDao {

    private Connection connection;
    
    private PreparedStatement addCandleStmt;
    private PreparedStatement getAllCandlesStmt;
    private PreparedStatement deleteCandleStmt;
    private PreparedStatement getCandleStmt;
    private PreparedStatement updateCandleStmt;

    public CandleDaoJdbcImpl(Connection connection) throws SQLException {
        this.connection = connection;
        setConnection(connection);
    }

    public CandleDaoJdbcImpl() throws SQLException {

    }

    public void createTables() throws SQLException {
        connection.createStatement()
            .executeUpdate("CREATE TABLE " + "Candle(id bigint GENERATED BY DEFAULT AS IDENTITY, " + "name varchar(20) NOT NULL, " + "company varchar(30) NOT NULL, " + "cbt integer)");
    }

    private boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if ("Candle".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int addCandle(Candle candle) {
        int count = 0;
        try {
            addCandleStmt.setString(1, candle.getName());
            addCandleStmt.setString(2, candle.getCompany());
            addCandleStmt.setInt(3, candle.getCbt());
            count = addCandleStmt.executeUpdate();
            ResultSet generatedKeys = addCandleStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                candle.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
            }
            return count;
        }

        public List<Candle> getAllCandles() {
            List<Candle> candles = new LinkedList<>();
            try {
                ResultSet rs = getAllCandlesStmt.executeQuery();

                while (rs.next()) {
                    Candle c = new Candle();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setCompany(rs.getString("company"));
                    c.setCbt(rs.getInt("cbt"));
                    candles.add(c);
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
            }
            return candles;
        }

        @Override
        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) throws SQLException {
            this.connection = connection;
                addCandleStmt = connection.prepareStatement("INSERT INTO Candle (name, company, cbt) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                deleteCandleStmt = connection.prepareStatement("DELETE FROM Candle where id = ?");
                getAllCandlesStmt = connection.prepareStatement("SELECT id, name, company, cbt FROM Candle ORDER BY id");
                getCandleStmt = connection.prepareStatement("SELECT id, name, company, cbt FROM Candle WHERE id = ?");
                updateCandleStmt = connection.prepareStatement("UPDATE Candle SET name=?,company=?,cbt=? WHERE id = ?");
        }

    @Override
    public int deleteCandle(Candle candle) throws SQLException {
        int count = 0;
        try {
            deleteCandleStmt.setLong(1, candle.getId());
            count = deleteCandleStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        if (count <= 0)
            throw new SQLException("Candle not found to delete");
        return count;
    }

    @Override
    public int updateCandle(Candle candle) throws SQLException {
        int count = 0;
        try {
            updateCandleStmt.setString(1, candle.getName());
            updateCandleStmt.setString(2, candle.getCompany());
            updateCandleStmt.setInt(3, candle.getCbt());
            if (candle.getId() != null) {
                updateCandleStmt.setLong(4, candle.getId());
            } else {
                updateCandleStmt.setLong(4, -1);
            }
            count = updateCandleStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        if (count <= 0)
            throw new SQLException("Candle not found for update");
        return count;
    }

    @Override
    public Candle getCandle(long id) throws SQLException {
        try {
            getCandleStmt.setLong(1, id);
            ResultSet rs = getCandleStmt.executeQuery();

            if (rs.next()) {
                Candle c = new Candle();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setCompany(rs.getString("company"));
                c.setCbt(rs.getInt("cbt"));
                return c;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Candle with id " + id + " does not exist");
    }

}