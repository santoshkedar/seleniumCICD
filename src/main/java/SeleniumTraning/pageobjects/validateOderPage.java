package SeleniumTraning.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumTraining.abstractcomponent.Abstractcomponent;

public class validateOderPage extends Abstractcomponent
{
	WebDriver driver;
	
	public validateOderPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> orderProductNames;
	
	public boolean validateOrderproducts(String productName)
	{
        boolean match = orderProductNames.stream().anyMatch(cartprod->cartprod.getText().equalsIgnoreCase(productName));
        return match;
	}
	
}
