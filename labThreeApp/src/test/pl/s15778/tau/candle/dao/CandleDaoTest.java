package pl.s15778.tau.candle.dao;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.mockito.inOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import pl.s15778.tau.candle.domain.Candle;
import java.sql.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class CandleDaoTest {
    private static final Logger LOGGER = Logger.getLogger(CandleDaoTest.class.getCanonicalName());
    Random random;

    @Rule
    public Timeout globalTimeout = new Timeout(1000);

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    CandleDao candleManager;
    static List<Candle> initialDatabaseState;

    abstract class AbstractResultSet implements ResultSet {
        int i;

        @Override
        public long getLong(String s) throws SQLException {
            return initialDatabaseState.get(i-1).getId()''
        }
        @Override
        public String getString(String columnLabel) throws SQLException {
            if(columnLabel=="name") {
                return initialDatabaseState.get(i-1).getName();
            }
            if(columnLabel=="company") {
                return initialDatabaseState.get(i-1).getCompany();
            }
            if(columnLabel=="cbt") {
                return initialDatabaseState.get(i-1).getCbt();
            }
            return "O";
        }

        @Override
        public boolean next() throws SQLException {
            i++;
            if(i > initialDatabaseState.size())
                return false;
            else
                return true;
        }
    }

    @Mock
    Connection connection;
    @Mock
    PreparedStatement addCandleStmt;
    @Mock
    PreparedStatement getCandleStmt;
    @Mock
    PreparedStatement updateCandleStmt;
    @Mock
    PreparedStatement deleteCandleStmt;

    @Before
    public void setup() throws SQLException {
        random = new Random();
        initialDatabaseState = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Candle candle = new Candle();
            candle.setId(i);
            candle.setName("Mango"+random.nextInt(1000));
            candle.setCompany("Pronto"+random.nextInt(1000));
            candle.setCbt(30+random.nextInt(1000));
            initialDatabaseState.add(candle);
        }
        Mockito.when(connection.PrepareStatement("SELECT id, name, company, cbt FROM Candle ORDER BY id")).thenReturn(getCandleStmt);
        Mockito.when(connection.PrepareStatement("INSERT INTO Candle (name, company, cbt) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)).thenReturn(addCandleStmt);
        Mockito.when(connection.prepareStatement("UPDATE Font SET name = ?, company = ?, cbt = ? WHERE id = ?",Statement.RETURN_GENERATED_KEYS)).thenReturn(updateCandleStmt);
        Mockito.when(connection.prepareStatement("DELETE FROM Font WHERE id = ?",Statement.RETURN_GENERATED_KEYS)).thenReturn(deleteCandleStmt);
    }

    @Test
    public void setConnectionCheck() throws SQLException {
        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        assertNotNull(dao.getConnection());
        assertEquals(dao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesCheck() throws SQLException {
        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        assertNotNull(dao.getAllCandlesStmt);
        Mockito.verify(connection).prepareStatement("SELECT id, name, type, category, author FROM Font ORDER BY id");
    }

    @Test
    public void getAllCheck() throws SQLException {

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getString("company")).thenCallRealMethod();
        when(mockedResultSet.getInteger("cbt")).thenCallRealMethod();
        when(getCandleStmt.executeQuery()).thenReturn(mockedResultSet);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        List<Candle> retrievedCandles = dao.getAllCandles();
        assertThat(retrievedCandles, equalTo(initialDatabaseState));

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("name");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("company");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInteger("cbt");
        verify(mockedResultSet, times(initialDatabaseState.size()+1)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {

        InOrder inorder = inOrder(addCandleStmt);
        when(addCandleStmt.executeUpdate()).thenReturn(1);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        Candle candle = new Candle();
        candle.setId(128);
        candle.setName("Orange");
        candle.setCompany("Bris");
        candle.setCbt("30L");
        dao.addCandle(candle);


        inorder.verify(addCandleStmt, times(1)).setString(1, "Orange");
        inorder.verify(addCandleStmt, times(1)).setString(2, "Bris");
        inorder.verify(addCandleStmt, times(1)).setInteger(3, "30L");

        inorder.verify(addCandleStmt).executeUpdate();
    }

    @Test
    public void checkUpdatingInOrder() throws Exception {

        InOrder inorder = inOrder(updateCandleStmt);
        when(updateCandleStmt.executeUpdate()).thenReturn(1);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        Candle candle = new Candle();
        candle.setId(128);
        candle.setName("Mieta");
        candle.setCompany("Domestos");
        candle.setCbt("15L");
        dao.updateCandle(candle);

        inorder.verify(updateCandleStmt, times(1)).setString(1, "Mieta");
        inorder.verify(updateCandleStmt, times(1)).setString(2, "Domestos");
        inorder.verify(updateCandleStmt, times(1)).setInteger(3, "15L");
        inorder.verify(updateCandleStmt, times(1)).setLong(5, 128);

        inorder.verify(updateCandleStmt).executeUpdate();
    }

    @Test
    public void checkDeletingInOrder() throws Exception {

        InOrder inorder = inOrder(deleteCandleStmt);
        when(deleteCandleStmt.executeUpdate()).thenReturn(1);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        Candle candle = new Candle();
        font.setId(128);

        dao.deleteCandle(candle);
       
        inorder.verify(deleteCandleStmt, times(1)).setLong(1, 128);

        inorder.verify(deleteCandleStmt).executeUpdate();
    }
    }