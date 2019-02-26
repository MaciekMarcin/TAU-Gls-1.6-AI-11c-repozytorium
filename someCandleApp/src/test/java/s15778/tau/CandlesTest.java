package s15778.tau;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CandlesTest{


@Test
public void CandlesSumCheck() {

    Candles can = new Candles();
    assertEquals(4.0, can.CandleSum(2.0, 2.0), 0.001);
}
  
@Test
public void CandlesLoopSumCheck() {

    Candles can = new Candles();
    assertEquals(1.0, can.CandleLoopSum(), 0.001);
}

}