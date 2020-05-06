Feature: SDET interview challenge
# I know this is not the best way to use Given, When, Then, but since there were a lot of steps and I wanted
# to match them exactly as in the email, I didn't give much importance to the first keyword on each step.
  Scenario: The Challenge
    Given Go to "https://www.microsoft.com/en-us/"
    Then Validate all menu items are present:
      | Office  |
      | Windows |
      | Surface |
      | Xbox    |
      | Deals   |
      | Support |
    And Go to Windows
    And Once in Windows page, click on Windows 10 Menu
    And Print all Elements in the dropdown
    And Go to Search next to the shopping cart
    And Search for "Visual Studio"
    And Print the price for the 3 first elements listed in "Software" result list
    And Store the price of the first one
    And Click on the first one to go to the details page
    And Once in the details page, validate both prices are the same
    And Click Add To Cart
    Then Verify all 3 price amounts are the same
    And On the # of items dropdown select "20" and validate the Total amount is Unit Price * 20