Feature: Candle delivery
  Delivery of new candles to the candle store

  Scenario: Delivery of candles
    Given Delivery of 300 candles
    When Candles has been delivered
    Then Quantity of candles has been increased by 300