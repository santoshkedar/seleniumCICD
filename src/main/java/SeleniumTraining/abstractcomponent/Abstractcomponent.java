package SeleniumTraining.abstractcomponent;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import SeleniumTraning.pageobjects.validateCart;
import SeleniumTraning.pageobjects.validateOderPage;

public class Abstractcomponent 
{
	
	WebDriver driver;
	
	public Abstractcomponent(WebDriver driver) 
	{
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(css="[routerlink*='cart']")
	WebElement clickViewCart;
	
	@FindBy(css="[routerlink*='/dashboard/myorders']")
	WebElement clickOrderCart;

	public void waitForElementToLocate(By Findby)
		{
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
			wait.until(ExpectedConditions.visibilityOfElementLocated(Findby));
		}
	
	public void waitForVisibilityOfElement(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public void waitForInvisibilityOfElement(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public validateCart goToCartPage()
	{
		clickViewCart.click();
		validateCart cart = new validateCart(driver);
		return cart;
	}
	
	public validateOderPage goToOrderPage()
	{
		clickOrderCart.click();
		validateOderPage orderCart = new validateOderPage(driver);
		return orderCart;
	}
	
}
