Scenario: Customer is looking for a candle by filtering
Given Customer is on page with candles search engine
When Customer sets the name filtering to Mango
And Customer sets the company filtering to Ikea
And Customer sets cbt filtering to 30
Then Customer finds candles that meet the criteria given by him