package pl.s15778.tau.candle.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class CandleTest {
    @Test
    public void createObjectTest(){
        Candle c = new Candle();
        assertNotNull(c);
    }

    @Test
    public void candleGettersAndSettersTest() {
        Candle c = new Candle();
        c.setId(1);
        c.setName("Lawendowe wzgorza");
        c.setCompany("Ikea");
        c.setBurningTime(30);
        assertEquals(1, c.getId());
        assertEquals("Lawendowe wzgorza", c.getName());
        assertEquals("Ikea", c.getCompany());
        assertEquals(30, c.getBurningTime());
    }
}
