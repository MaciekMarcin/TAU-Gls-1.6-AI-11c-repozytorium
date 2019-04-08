Feature: Candle light
    Customer lights a candle

    Scenario Outline: Customer lights a candle
        Given Customer has a candle
        When Customer chose name "<name>"
        Then Candle has been lit

        Examples:
        | name    |
        | Mango   |
        | Lawenda |
        | Mięta   |