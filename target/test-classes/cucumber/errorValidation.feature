
@tag
Feature: Error Validation
  I want to use this template for my feature file

  @tag2
  Scenario Outline: Negative Test for Error Validation
    Given I landed on Ecommerce page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." error message displayed

     Examples: 
      | name  						|			 password		 |
      | santosh@gmail.com | 		 S@ntosh  	 |
