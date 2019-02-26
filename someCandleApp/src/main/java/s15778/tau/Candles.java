package s15778.tau;

/**
 * Hello world!
 *
 */
public class Candles
{
    public Double CandleSum(Double c1, Double c2) {
        return c1+c2;
    }

    public Double CandleLoopSum() {
        double sum = 0.0;
        for(int i = 10; i>0; i--) {
            sum += 0.1;
        }
        return sum;
    }
}
