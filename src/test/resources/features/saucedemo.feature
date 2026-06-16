Feature: Saucedemo Web Automation

  Scenario: Positive - Successful Login
    Given User is on the login page
    When User inputs valid username "standard_user" and password "secret_sauce"
    And User clicks login button
    Then User is redirected to the products page

  Scenario: Negative - Failed Login with Invalid Credentials
    Given User is on the login page
    When User inputs invalid username "invalid_user" and password "wrong_pass"
    And User clicks login button
    Then Error message should be displayed

  Scenario: Sort Products by Price (Low to High)
    Given User is on the products page
    When User selects sort option "Price (low to high)"
    Then The products should be sorted ascending by price

  Scenario: Sort Products by Price (High to Low)
    Given User is on the products page
    When User selects sort option "Price (high to low)"
    Then The products should be sorted descending by price

  Scenario: Add Product to Cart
    Given User is on the products page
    When User clicks add to cart on the first product
    Then The cart badge should increase