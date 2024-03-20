package SeleniumTraning.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumTraining.abstractcomponent.Abstractcomponent;

public class LandingPage extends Abstractcomponent
{
	WebDriver driver;
	int count=0;
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public ProductCatalogue loginToApplication(String userName, String pass)
	{
		userEmail.sendKeys(userName);
		password.sendKeys(pass);
		submit.click();
		ProductCatalogue cataLouge = new ProductCatalogue(driver);
		count++;
		System.out.println(count);
		return cataLouge;
	}
	
	public String getErrorMessage() throws InterruptedException
	{
		waitForVisibilityOfElement(errorMessage);
		return errorMessage.getText();
	}
		
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
		
}
