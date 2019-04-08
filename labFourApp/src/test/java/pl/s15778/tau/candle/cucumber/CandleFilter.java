package pl.s15778.tau.candle.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.s15778.tau.candle.dao.CandleInMemoryDao;
import pl.s15778.tau.candle.domain.Candle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CandleFilter {

    private CandleInMemoryDao candleInMemoryDao;
    private List<Candle> filteredCandles;
    private String choosedName;
    private String choosedCompany;
    private Integer choosedCbt;

    @Given("^Customer is on page with candles search engine$")
    public void customer_is_on_page_with_candles_search_engine() {
        candleInMemoryDao = new CandleInMemoryDao();
        candleInMemoryDao.candles = new ArrayList<>();
        Collections.addAll(candleInMemoryDao.candles,
                new Candle(1L, "Truskawka", "Pronto", 45),
                new Candle(2L, "Mango", "Ikea", 25),
                new Candle(3L, "Wanilia", "Domestos", 15),
                new Candle(4L, "Mango", "Ikea", 30),
                new Candle(5L, "Mango", "Ikea", 45),
                new Candle(6L, "Mango", "Ikea", 30),
                new Candle(7L, "Mango", "Ikea", 30),
                new Candle(8L, "Mango", "Bris", 30),
                new Candle(9L, "Mango", "Ikea", 30));
        filteredCandles = candleInMemoryDao.getAll();
    }

    @When("^Customer sets the name filtering to \"([^\"]*)\"$")
    public void customer_sets_the_name_filtering_to(String name) {
        choosedName = name;
        filteredCandles = filteredCandles.parallelStream().filter(candle -> candle.getName().equals(choosedName)).collect(Collectors.toList());
    }

    @And("^Customer sets the company filtering to \"([^\"]*)\"$")
    public void customer_sets_the_company_filtering_to(String company) {
        choosedCompany = company;
        filteredCandles = filteredCandles.parallelStream().filter(candle -> candle.getCompany().equals(choosedCompany)).collect(Collectors.toList());
    }

    @And("^Customer sets cbt filtering to (\\d+)$")
    public void user_sets_cbt_filtering_to(int cbt) {
        choosedCbt = cbt;
        filteredCandles = filteredCandles.parallelStream().filter(candle -> candle.getCbt() <= choosedCbt).collect(Collectors.toList());
    }

    @Then("^Customer finds candles that meet the criteria given by him$")
    public void customer_finds_candles_that_meet_the_criteria_given_by_him() {
        Assert.assertEquals(5, filteredCandles.size());
    }

    @But("^Customer shouldn't see candles that not meet criteria$")
    public void customer_shouldnt_see_candles_that_not_meet_criteria() {
        Assert.assertFalse(filteredCandles.contains(candleInMemoryDao.getById(1L)));
        Assert.assertFalse(filteredCandles.contains(candleInMemoryDao.getById(2L)));
        Assert.assertFalse(filteredCandles.contains(candleInMemoryDao.getById(3L)));
        Assert.assertFalse(filteredCandles.contains(candleInMemoryDao.getById(5L)));
        Assert.assertFalse(filteredCandles.contains(candleInMemoryDao.getById(8L)));
    }

}