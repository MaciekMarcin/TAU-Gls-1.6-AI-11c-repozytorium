package pl.s15778.tau.candle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.s15778.tau.candle.dao.CandleInMemoryDao;
import pl.s15778.tau.candle.domain.Candle;

import java.util.ArrayList;
import java.util.Collections;

public class CandleLight {
    
    private CandleInMemoryDao candleInMemoryDao;
    private String choosedName;

    @Given("^Customer has a candle$")
    public void customer_chooses_a_candle() {
        candleInMemoryDao = new CandleInMemoryDao();
        candleInMemoryDao.candles = new ArrayList<>();
        Collections.addAll(candleInMemoryDao.candles, new Candle(1L, "Mango", "Bris", 30), new Candle(2L, "Lawenda", "Pronto", 45), new Candle(3L, "Mięta", "Domestos", 15));
    }

    @When("^Customer chose name \"([^\"]*)\"$")
    public void customer_choose_name(String name) {
        choosedName = name;
    }

    @Then("^Candle has been lit$")
    public void candle_has_been_lit() {
        Candle choosedCandle = candleInMemoryDao.getAll().stream().filter(candle -> candle.getName().equals(choosedName)).findFirst().get();
        Assert.assertEquals(choosedCandle, candleInMemoryDao.getById(choosedCandle.getId()).get());
        candleInMemoryDao.delete(choosedCandle);
        Assert.assertEquals(2, candleInMemoryDao.candles.size());
    }
}