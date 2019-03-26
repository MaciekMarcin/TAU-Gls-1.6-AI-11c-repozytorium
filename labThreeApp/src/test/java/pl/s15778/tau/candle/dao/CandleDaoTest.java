package pl.s15778.tau.candle.dao;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
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
    CandleDao candleManager;
    static List<Candle> initialDatabaseState;

    abstract class AbstractResultSet implements ResultSet {
        int i;

        @Override
        public long getLong(String s) throws SQLException {
            return initialDatabaseState.get(i-1).getId();
        }
        @Override
        public String getString(String columnLabel) throws SQLException {
            if(columnLabel=="name") {
                return initialDatabaseState.get(i-1).getName();
            }
            if(columnLabel=="company") {
                return initialDatabaseState.get(i-1).getCompany();
            }
            return "O";
        }

        @Override
        public int getInt(String s) throws SQLException {
            return initialDatabaseState.get(i-1).getCbt();
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
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement updateStatementMock;
    @Mock
    PreparedStatement deleteStatementMock;

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
        Mockito.when(connection.prepareStatement("SELECT id, name, company, cbt FROM Candle ORDER BY id")).thenReturn(selectStatementMock);
        Mockito.when(connection.prepareStatement("INSERT INTO Candle (name, company, cbt) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        Mockito.when(connection.prepareStatement("UPDATE Candle SET name=?,company=?,cbt=? WHERE id = ?")).thenReturn(updateStatementMock);
        Mockito.when(connection.prepareStatement("DELETE FROM Candle where id = ?")).thenReturn(deleteStatementMock);
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
        Mockito.verify(connection).prepareStatement("SELECT id, name, company, cbt FROM Candle ORDER BY id");
    }

    @Test
    public void getAllCheck() throws SQLException {

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getString("company")).thenCallRealMethod();
        when(mockedResultSet.getInt("cbt")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        List<Candle> retrievedCandles = dao.getAllCandles();
        assertThat(retrievedCandles, equalTo(initialDatabaseState));

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("name");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("company");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("cbt");
        verify(mockedResultSet, times(initialDatabaseState.size()+1)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {

        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        Candle candle = new Candle();
        candle.setId(128);
        candle.setName("Orange");
        candle.setCompany("Bris");
        candle.setCbt(30);
        dao.addCandle(candle);


        inorder.verify(insertStatementMock, times(1)).setString(1, "Orange");
        inorder.verify(insertStatementMock, times(1)).setString(2, "Bris");
        inorder.verify(insertStatementMock, times(1)).setInt(3, 30);

        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void checkUpdatingInOrder() throws Exception {

        InOrder inorder = inOrder(updateStatementMock);
        when(updateStatementMock.executeUpdate()).thenReturn(1);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);

        Candle candle = new Candle();
        candle.setId(128);
        candle.setName("Mieta");
        candle.setCompany("Domestos");
        candle.setCbt(15);
        dao.updateCandle(candle);

        inorder.verify(updateStatementMock, times(1)).setString(1, "Mieta");
        inorder.verify(updateStatementMock, times(1)).setString(2, "Domestos");
        inorder.verify(updateStatementMock, times(1)).setInt(3, 15);
        inorder.verify(updateStatementMock, times(1)).setLong(4, 128);

        inorder.verify(updateStatementMock).executeUpdate();
    }

    @Test
    public void checkDeletingInOrder() throws Exception {

        InOrder inorder = inOrder(deleteStatementMock);
        when(deleteStatementMock.executeUpdate()).thenReturn(1);

        CandleDaoJdbcImpl dao = new CandleDaoJdbcImpl();
        dao.setConnection(connection);
        Candle candle = new Candle();
        candle.setId(128);
        dao.deleteCandle(candle);
       
        inorder.verify(deleteStatementMock, times(1)).setLong(1, 128);

        inorder.verify(deleteStatementMock).executeUpdate();
    }
    }