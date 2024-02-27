package SeleniumTraning.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import SeleniumTraning.pageobjects.ProductCatalogue;
import SeleniumTraning.pageobjects.validateCart;
import SelenumTraning.testcomponents.BaseTest;
import SelenumTraning.testcomponents.Retry;

public class errorValidationLoginPageTest extends BaseTest
{
	
	String productName="ADIDAS ORIGINAL";
	
	@Test (retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException
	{
	
		landingPage.loginToApplication("santosh@gmail.com","Satosh1");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
	}
	
	@Test
	public void productErrorValidation() throws IOException, InterruptedException
	{
		
		ProductCatalogue cataLouge = landingPage.loginToApplication("kedar@gmail.com","Kedar!23");
		cataLouge.getProductList();
		cataLouge.addToCart(productName);
		
		validateCart cart = cataLouge.goToCartPage();
		boolean match = cart.validateCartproducts("ADIDAS ORIGINALs");
		Assert.assertFalse(match);
      }

}
