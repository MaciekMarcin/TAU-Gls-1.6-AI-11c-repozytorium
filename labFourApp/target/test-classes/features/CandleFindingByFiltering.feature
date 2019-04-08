Feature: Candle finding by filtering
  Customer is looking for a candles by filtering

  Scenario: Customer is looking for a candle by filtering
    Given Customer is on page with candles search engine
    When Customer sets the name filtering to "Wanilia"
    And Customer sets the company filtering to "Pronto"
    And Customer sets cbt filtering to 50
    Then Customer finds candles that meet the criteria given by him