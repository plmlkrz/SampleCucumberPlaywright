#language: en
#author: peter.krzywicki@gmail.com

@inventory
Feature: Swag Labs Product Page
  As a user of the Swag Labs shop,
  I want to be able to sort products,
  so that I can find what I need.

  @sort
  Scenario Outline: Product sorting
    Given I am logged in as a standard user
    When I sort products by "<sortValue>"
    Then the first product name should be "<expectedFirstItem>"

    Examples:
      | sortValue | expectedFirstItem                      |
      | za        | Test.allTheThings() T-Shirt (Red)      |
      | az        | Sauce Labs Backpack                    |
      | hilo      | Sauce Labs Fleece Jacket               |
      | lohi      | Sauce Labs Onesie                      |

  @product-name
  Scenario: Verify first product after Z-A sort
    Given I am logged in as a standard user
    When I sort products by "za"
    Then the first product name should be "Test.allTheThings() T-Shirt (Red)"
