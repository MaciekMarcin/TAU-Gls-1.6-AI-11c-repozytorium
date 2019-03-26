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
    Candle candle = new Candle();
    candle.setId(1);
    candle.setName("Mango");
    candle.setCompany("AJAX");
    candle.setCbt(30);
    assertEquals(1, candle.getId().longValue());
    assertEquals("Mango", candle.getName());
    assertEquals("AJAX", candle.getCompany());
    assertEquals(30, candle.getCbt().intValue());
}
}