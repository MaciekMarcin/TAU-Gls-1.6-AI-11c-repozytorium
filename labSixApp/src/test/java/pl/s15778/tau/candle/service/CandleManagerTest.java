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

import pl.s15778.tau.candle.domain.Candle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
@Transactional(transactionManager = "txManager")
public class CandleManagerTest {

    @Autowired
    CandleManager candleManager;

    List<Long> candleIds;

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
        addCandleHelper("Mango", "Bris", 30);
        Candle candle = addCandleHelper("Mieta", "Pronto", 15);
    }

    @Test
    public void correctSetupTest() {
        assertTrue(candleIds.size() > 0);
    }

    @Test
    public void addCandleTest() {
        int prevSize = candleManager.findAllCandles().size();
        Candle candle = addCandleHelper("Wanilia", "Ikea", 45);
        assertEquals(prevSize+1,candleManager.findAllCandles().size());
    }

    @Test
    public void getAllCandlesTest() {
        List <Long> foundIds = new LinkedList<>();
        for (Candle candle: candleManager.findAllCandles()) {
            if (candleIds.contains(candle.getId())) foundIds.add(candle.getId());
        }
        assertEquals(candleIds.size(), foundIds.size());
    }

    @Test
    public void getCandleByIdTest() {
        Candle candle = candleManager.findCandleById(candleIds.get(0));
        assertEquals("Mango",candle.getName());
    }

    @Test
    public void deleteCandleTest() {
        int prevSize = candleManager.findAllCandles().size();
        Candle candle = candleManager.findCandleById(candleIds.get(0));
        assertNotNull(candle);
        candleManager.deleteCandle(candle);
        assertNull(candleManager.findCandleById(candleIds.get(0)));
        assertEquals(prevSize-1,candleManager.findAllCandles().size());
    }

    @Test
    public void updateCandleTest() {
        Candle candle = candleManager.findCandleById(candleIds.get(0));
        candle.setName("Lawenda");
        candle.setCompany("BRIES");
        candle.setCbt(25);
        candleManager.updateCandle(candle);
        Candle updated = candleManager.findCandleById(candleIds.get(0));
        assertEquals(updated.getName(), "Lawenda");
        assertEquals(updated.getCompany(), "BRIES");
        assertEquals(updated.getCbt().intValue(), 25);
    }

    @Test
    public void findCandlesByNameFragment() {
        List<Candle> candles = candleManager.findCandles("ng");
        assertEquals("Mango",candles.get(0).getName());
    }
}