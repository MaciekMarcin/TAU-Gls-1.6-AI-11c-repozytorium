Scenario: Customer buys a candle

Given Customer chooses a candle
When Customer chose company <company>
And Customer chose cbt <cbt>
Then Candle has been sold

Examples:
| company    | cbt |
| Bris       | 30  |
| Pronto     | 45  |
| Domestos   | 15  | 