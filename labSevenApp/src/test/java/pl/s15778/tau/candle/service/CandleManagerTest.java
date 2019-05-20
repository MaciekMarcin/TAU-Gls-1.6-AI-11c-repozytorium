package pl.s15778.tau.candle.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.s15778.tau.candle.domain.CandleStick;
import pl.s15778.tau.candle.domain.Candle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
@Transactional(transactionManager = "txManager")
public class CandleManagerTest {

    @Autowired
    CandleManager candleManager;

    List<Long> candleIds;
    List<Long> candleStickIds;

    public CandleStick addCandleStickHelper(String nameStick, int space, List<Candle> havingCandles) {
        Long candleStickId;
        CandleStick candleStick;
        candleStick = new CandleStick();
        candleStick.setNameStick(nameStick);
        candleStick.setSpace(space);
        candleStick.setCandles(havingCandles);
        candleStickIds.add(candleStickId = candleManager.addCandleStick(candleStick));
        assertNotNull(candleStickId);
        return candleStick;
    }

    /**
	 * Helper method allowing for easier adding candles to tests
	 * @param name
     * @param company
     * @param cbt
	 * @return
	 */
    public Candle addCandleHelper(String name, String company, int cbt) {
        Long candleId;
        Candle candle;
        candle = new Candle();
        candle.setName(name);
        candle.setCompany(company);
        candle.setCbt(cbt);
        candleIds.add(candleId = candleManager.addCandle(candle));
        assertNotNull(candleId);
        return candle;
    }

    @Before
    public void setup() {
        candleIds = new LinkedList<>();
        candleStickIds = new LinkedList<>();

        Candle candle1 = addCandleHelper("Mango", "Bris", 30);
        Candle candle2 = addCandleHelper("Kebab", "Lux", 50);
        addCandleHelper("Mieta", "Pronto", 15);

        ArrayList<Candle> testCandles = new ArrayList<Candle>();
        testCandles.add(candle1);
        testCandles.add(candle2);

        addCandleStickHelper("Żółw", 4, new LinkedList<Candle>());
        addCandleStickHelper("Żydowski", 7, testCandles);

    }

    @Test
    public void correctSetupTest() {
        assertTrue(candleStickIds.size() > 0);
    }

    @Test
    public void addCandleStickTest() {
        int prevSizeStick = candleManager.findAllCandleStick().size();
        CandleStick candleStick = addCandleStickHelper("Normalny", 4, new LinkedList<Candle>());
        assertEquals(prevSizeStick+1,candleManager.findAllCandleStick().size());
    }

    @Test
    public void addCandleTest() {
        int prevSize = candleManager.findAllCandles().size();
        Candle candle = addCandleHelper("Granat", "Ikea", 45);
        assertEquals(prevSize+1,candleManager.findAllCandles().size());
    }

    @Test
    public void getAllCandleStickTest() {
        List <Long> foundStickIds = new LinkedList<>();
        for (CandleStick candleStick: candleManager.findAllCandleStick()) {
            if (candleStickIds.contains(candleStick.getId())) foundStickIds.add(candleStick.getId());
        }
        assertEquals(candleStickIds.size(), foundStickIds.size());
    }

    @Test
    public void getCandleStickByIdTest() {
        CandleStick candleStick = candleManager.findCandleStickById(candleStickIds.get(0));
        assertEquals("Żółw",candleStick.getNameStick());
    }

    @Test
    public void deleteCandleStickTest() {
        int prevSizeStick = candleManager.findAllCandleStick().size();
        CandleStick candleStick = candleManager.findCandleStickById(candleStickIds.get(0));
        assertNotNull(candleStick);
        candleManager.deleteCandleStick(candleStick);
        assertNull(candleManager.findCandleStickById(candleStickIds.get(0)));
        assertEquals(prevSizeStick-1,candleManager.findAllCandleStick().size());
    }

    @Test
    public void updateCandleStickTest() {
        CandleStick candleStick = candleManager.findCandleStickById(candleStickIds.get(0));
        candleStick.setNameStick("Tripod");
        candleStick.setSpace(3);
        candleManager.updateCandleStick(candleStick);
        CandleStick updatedStick = candleManager.findCandleStickById(candleStickIds.get(0));
        assertEquals(updatedStick.getNameStick(), "Tripod");
        assertEquals(updatedStick.getSpace().intValue(), 3);
    }

    @Test
    public void findCandleStickByNameFragment() {
        List<CandleStick> candlesStick = candleManager.findCandleStick("dow");
        assertEquals("Żydowski",candlesStick.get(0).getNameStick());
    }

    @Test
    public void getCandlesOfCandleStick() {
        CandleStick candleStick = candleManager.findCandleStickById(candleStickIds.get(1));
        assertEquals(2, candleStick.getCandles().size());
    }

    @Test
    public void addCandlesToCandleStick() {
        CandleStick candleStick = candleManager.findCandleStickById(candleStickIds.get(0));
        Candle candle = addCandleHelper("Marakuja", "Box", 10);
        assertNotNull(candle);
        candleStick.addCandle(candle);
        assertEquals(1, candleStick.getCandles().size());
    }

    @Test
    public void deleteCandlesOfCandleStick() {
        CandleStick candleStick = candleManager.findCandleStickById(candleStickIds.get(1));
        Candle candle = candleManager.findCandleById(candleIds.get(0));
        assertNotNull(candle);
        candleStick.removeCandle(candle);
        assertEquals(1, candleStick.getCandles().size());
    }

    @Test
    public void moveCandles() {
        CandleStick candleStick1 = candleManager.findCandleStickById(candleStickIds.get(0));
        CandleStick candleStick2 = candleManager.findCandleStickById(candleStickIds.get(1));
        Candle candle = candleManager.findCandleById(candleIds.get(0));
        candleManager.moveCandles(candleStick1, candleStick2, candle);
        assertEquals(1, candleManager.findCandleStickById(candleStickIds.get(0)).getCandles().size());
        assertEquals(1, candleManager.findCandleStickById(candleStickIds.get(1)).getCandles().size());
        assertEquals(candle, candleManager.findCandleStickById(candleStickIds.get(0)).getCandles().get(0));
    }
}