package pl.s15778.tau.candle.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import pl.s15778.tau.candle.domain.Candle;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RunWith(BlockJUnit4ClassRunner.class)
public class CandleDaoTest {


    CandleDao candleDao;

    @Before
    public void setup() {
        candleDao = new CandleDao();
        /*Candle c1 = new Candle();
        Candle c2 = new Candle();
        c1.setId(1L);
        c1.setName("Pomaranczowe mango");
        c1.setCompany("Drutex");
        c1.setBurningTime(15L);
        c2.setId(2L);
        c2.setName("Chyba owoce lesne");
        c2.setCompany("Bazar");
        c2.setBurningTime(45L);
        dao.candles = new HashMap<Long, Candle>();
        dao.candles.put(1L,c1);
        dao.candles.put(2L,c2);*/
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(candleDao);
    }
}