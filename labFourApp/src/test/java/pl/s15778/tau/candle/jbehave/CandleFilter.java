package pl.s15778.tau.candle.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
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

    @Given("Customer is on page with candles search engine")
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

    @When("Customer sets the name filtering to $name")
    public void customer_sets_the_name_filtering_to(String name) {
        choosedName = name;
        filteredCandles = filteredCandles.parallelStream().filter(candle -> candle.getName().equals(choosedName)).collect(Collectors.toList());
    }

    @When("Customer sets the company filtering to $company")
    public void customer_sets_the_company_filtering_to(String company) {
        choosedCompany = company;
        filteredCandles = filteredCandles.parallelStream().filter(candle -> candle.getCompany().equals(choosedCompany)).collect(Collectors.toList());
    }

    @When("Customer sets cbt filtering to $cbt")
    public void user_sets_cbt_filtering_to(int cbt) {
        choosedCbt = cbt;
        filteredCandles = filteredCandles.parallelStream().filter(candle -> candle.getCbt() <= choosedCbt).collect(Collectors.toList());
    }

    @Then(value = "Customer finds candles that meet the criteria given by him", priority = 1)
    public void customer_finds_candles_that_meet_the_criteria_given_by_him() {
        Assert.assertEquals(5, filteredCandles.size());
    }

}