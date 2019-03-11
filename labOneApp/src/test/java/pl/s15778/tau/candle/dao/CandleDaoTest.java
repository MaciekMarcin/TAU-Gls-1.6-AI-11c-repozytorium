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

    CandleDao dao;

    @Before
    public void setup() {
        Candle c1 = new Candle();
        Candle c2 = new Candle();
        c1.setId(1L);
        c1.setName("Lawendowe wzgorza");
        c1.setCompany("Bris");
        c1.setBurningTime(30L);
        c2.setId(2L);
        c2.setName("Pomaraczowa sciana");
        c2.setCompany("Pronto");
        c2.setBurningTime(20L);
        dao = new CandleDao();
        dao.candles = new HashMap<Long, Candle>();
        dao.candles.put(1L,c1);
        dao.candles.put(2L,c2);
    }

    @Test
    public void candleDaoExistsTest() {
        assertNotNull(dao);
    }

    @Test
    public void getCandleThatExistsTest(){
        Optional<Candle> c = dao.get(1L);
        assertThat(c.get().getName(), is("Lawendowe wzgorza"));
    }

    @Test
    public void saveNewCandleTest(){
        Candle c3 = new Candle();
        c3.setId(3L);
        c3.setName("Mietowa lazienka");
        c3.setCompany("Drutex");
        c3.setBurningTime(40L);
        dao.save(c3);
        Optional<Candle> c = dao.get(c3.getId());
        assertThat(c.get().getName(), is("Mietowa lazienka"));
    }

    @Test
    public void updateExistingCandle() {
        Candle updatedCandle = new Candle();
        updatedCandle.setId(1L);
        updatedCandle.setName("Lody waniliowe");
        updatedCandle.setCompany("Panasonix");
        updatedCandle.setBurningTime(10L);
        dao.update(updatedCandle);
        Optional<Candle> c = dao.get(1L);
        assertThat(c.get().getName(), is("Lody waniliowe"));
    }

    @Test
    public void deleteExistingCandle() {
        Candle deletedCandle = new Candle();
        deletedCandle.setId(1L);
        deletedCandle.setName("Lody waniliowe");
        deletedCandle.setCompany("Panasonix");
        deletedCandle.setBurningTime(10L);
        dao.delete(deletedCandle);
        Optional<Candle> c = dao.get(deletedCandle.getId());
        assertThat(c, is(Optional.empty()));
        //assertNull(c);
    }

}