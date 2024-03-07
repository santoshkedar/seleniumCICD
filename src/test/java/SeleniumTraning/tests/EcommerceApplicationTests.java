package SeleniumTraning.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SeleniumTraning.pageobjects.ProductCatalogue;
import SeleniumTraning.pageobjects.checkOutPage;
import SeleniumTraning.pageobjects.confirmationPage;
import SeleniumTraning.pageobjects.validateCart;
import SeleniumTraning.pageobjects.validateOderPage;
import SelenumTraning.testcomponents.BaseTest;

public class EcommerceApplicationTests extends BaseTest
{
	//String productName="ADIDAS ORIGINAL";
	//Changed code for CICD
	//Changed code for CICD2
	
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap <String,String> input) throws IOException, InterruptedException
	{
		
		String country="India";
	
		ProductCatalogue cataLouge = landingPage.loginToApplication(input.get("email"), input.get("password"));
		//cataLouge.getProductList();
		cataLouge.addToCart(input.get("productName"));
		//cataLouge.addToCart(productName2);
		
		validateCart cart = cataLouge.goToCartPage();
		boolean match = cart.validateCartproducts(input.get("productName"));
		Assert.assertTrue(match);
		
		checkOutPage checkOutObject = cart.checkOut();
		checkOutObject.submitPaymentDetails(country);
		confirmationPage confirmationPageObject = checkOutObject.placeOrder();
		
		String confrimMessage= confirmationPageObject.getConfirmationPageMessage();
        Assert.assertTrue(confrimMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
      }
	
	@Test(dataProvider="getData", dependsOnMethods= {"submitOrder"})
	public void purchaseOrderValidation(HashMap <String,String> input)
	{
		ProductCatalogue cataLouge = landingPage.loginToApplication(input.get("email"), input.get("password"));	
		validateOderPage orderCart = cataLouge.goToOrderPage();
		boolean match = orderCart.validateOrderproducts(input.get("validateProductName"));
		Assert.assertTrue(match);
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		
		List<HashMap<String,String>> data= getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//SeleniumTraning//data//PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
//	@DataProvider
//	public Object[][] getData()
//	{
//		return new Object[][] {{""santosh@gmail.com", "S@ntosh1", "ADIDAS ORIGINAL" },{"kedar@gmail.com", "Kedar!23", "IPHONE 13 PRO"}};
	
//	HashMap<String,String> map = new HashMap<String,String>();
//	map.put("email", "santosh@gmail.com");
//	map.put("password", "S@ntosh1");
//	map.put("productName", "ADIDAS ORIGINAL");
//	
//	HashMap<String,String> map1 = new HashMap<String,String>();
//	map1.put("email", "kedar@gmail.com");
//	map1.put("password", "Kedar!23");
//	map1.put("productName", "IPHONE 13 PRO");
//	}

}
