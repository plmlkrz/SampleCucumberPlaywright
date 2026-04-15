#language: en
#author: peter.krzywicki@gmail.com

@login
Feature: Swag Labs Login
  As a Swag Labs customer,
  I need to be able to log in,
  so that I can purchase Sauce Labs merch.

  @login-success
  Scenario: Successful login with standard user
    Given I am on the login page
    When I login as "standard_user" with password "secret_sauce"
    Then I should see the inventory page
    And I should see the app logo

  @login-locked
  Scenario: Failed login with locked out user
    Given I am on the login page
    When I login as "locked_out_user" with password "secret_sauce"
    Then I should see an error message containing "Sorry, this user has been locked out."
