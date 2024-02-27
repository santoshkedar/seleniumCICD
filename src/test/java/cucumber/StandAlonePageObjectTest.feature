
@tag
Feature: Purchase the order from the Ecommerese Website
  I want to use this template for my feature file
  
Background:
		Given I landed on Ecommerce page

  @Regression
  Scenario Outline: Positive Test for submit order test
  
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message diplayed on confirmation page

    Examples: 
      | name  						|			 password		 | 			productName  	  |
      | santosh@gmail.com | 		 S@ntosh1 	 | 			ADIDAS ORIGINAL |
    
