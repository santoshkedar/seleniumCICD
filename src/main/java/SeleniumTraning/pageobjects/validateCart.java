package SeleniumTraning.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumTraining.abstractcomponent.Abstractcomponent;

public class validateCart extends Abstractcomponent
{
	WebDriver driver;
	
	public validateCart(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cart h3")
	List<WebElement> cartProducts;
	
	@FindBy(css=".subtotal button")
	WebElement clickCheckOut;
	
	public List<WebElement> getCartProducts()
	{
		return cartProducts;
	}
	
	public boolean validateCartproducts(String productName)
	{
        boolean match = getCartProducts().stream().anyMatch(cartprod->cartprod.getText().equalsIgnoreCase(productName));
        return match;
	}
	
	public checkOutPage checkOut()
	{
		clickCheckOut.click();
		checkOutPage checkOutObject = new checkOutPage(driver);
		return checkOutObject;
	}
	
}
