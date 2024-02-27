package SeleniumTraning.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import SeleniumTraining.abstractcomponent.Abstractcomponent;

public class checkOutPage extends Abstractcomponent
{
	WebDriver driver;
	
	public checkOutPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement country;
	
	@FindBy(css=".action__submit")
	WebElement submit;
	
	@FindBy(xpath="(//span[@class='ng-star-inserted'])[2]")
	WebElement selectCountry;
	
	By countryList=By.cssSelector(".ta-results");
	
	public void submitPaymentDetails(String countryName)
	{
		
		 Actions a = new Actions(driver);
	     a.sendKeys(country,countryName).build().perform();
	     waitForElementToLocate(countryList);
	     selectCountry.click();
	}
	
	public confirmationPage placeOrder()
	{
		submit.click();
		confirmationPage confirmationPageObject = new  confirmationPage(driver);
		return confirmationPageObject;
	}
	     
}
