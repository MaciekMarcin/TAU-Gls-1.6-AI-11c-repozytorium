package pl.s15778.tau.candle.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.s15778.tau.candle.dao.CandleInMemoryDao;
import pl.s15778.tau.candle.domain.Candle;

import java.util.ArrayList;
import java.util.Collections;

public class CandleBuying {

    private CandleInMemoryDao candleInMemoryDao;
    private String choosedCompany;
    private Integer choosedCbt;

    @Given("Customer chooses a candle")
    public void customer_chooses_a_candle() {
        candleInMemoryDao = new CandleInMemoryDao();
        candleInMemoryDao.candles = new ArrayList<>();
        Collections.addAll(candleInMemoryDao.candles, new Candle(1L, "Mango", "Bris", 30), new Candle(2L, "Lawenda", "Pronto", 45), new Candle(3L, "Mięta", "Domestos", 15));
    }

    @When("Customer chose company $company")
    public void customer_choose_company(String company) {
        choosedCompany = company;
    }

    @When("Customer chose cbt $cbt")
    public void customer_choose_cbt(Integer cbt) {
        choosedCbt = cbt;
    }

    @Then(value = "Candle has been sold", priority = 1)
    public void candle_has_been_sold() {
        Candle choosedCandle = candleInMemoryDao.getAll().stream().filter(candle -> candle.getCompany().equals(choosedCompany) && candle.getCbt().equals(choosedCbt)).findFirst().get();
        Assert.assertEquals(choosedCandle, candleInMemoryDao.getById(choosedCandle.getId()).get());
        candleInMemoryDao.delete(choosedCandle);
        Assert.assertEquals(2, candleInMemoryDao.candles.size());
    }
}