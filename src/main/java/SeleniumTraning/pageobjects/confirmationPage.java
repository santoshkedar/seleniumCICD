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

public class confirmationPage extends Abstractcomponent
{
	WebDriver driver;
	
	public confirmationPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".hero-primary")
	WebElement confirmMessageObject;
	
	public String getConfirmationPageMessage()
	{
		return confirmMessageObject.getText();
     
	}

}
