package SeleniumTraining.stepDefinations;

import java.io.IOException;

import org.testng.Assert;

import SeleniumTraning.pageobjects.LandingPage;
import SeleniumTraning.pageobjects.ProductCatalogue;
import SeleniumTraning.pageobjects.checkOutPage;
import SeleniumTraning.pageobjects.confirmationPage;
import SeleniumTraning.pageobjects.validateCart;
import SelenumTraning.testcomponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepDefinationImplementation extends BaseTest 
{
	public LandingPage landingPage;
	public ProductCatalogue cataLouge;
	public validateCart cart;
	public checkOutPage checkOutObject;
	public confirmationPage confirmationPageObject;
	
	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password)
	{
		cataLouge = landingPage.loginToApplication(username,password);
	}
	
	@When("^I add product (.+) to cart$")
	public void I_add_product_to_the_cart(String productName) throws InterruptedException
	{
		cataLouge.getProductList();
		cataLouge.addToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void Checkout_Submit_order(String productName)
	{
		
		cart = cataLouge.goToCartPage();
		boolean match = cart.validateCartproducts(productName);
		Assert.assertTrue(match);
		
		checkOutObject = cart.checkOut();
		checkOutObject.submitPaymentDetails("India");
		confirmationPageObject = checkOutObject.placeOrder();
	}
	
	@Then("{string} message diplayed on confirmation page")
	public void message_diplayed_on_confirmationPage(String string)
	{
		String confrimMessage= confirmationPageObject.getConfirmationPageMessage();
        Assert.assertTrue(confrimMessage.equalsIgnoreCase(string));
        driver.close();
	}
	
	@Then("{string} error message displayed")
	public void validate_ErrorMessage(String string)
	{
		Assert.assertEquals(string, landingPage.getErrorMessage());
        driver.close();
	}
	
}
