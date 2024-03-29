package SeleniumTraning.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumTraining.abstractcomponent.Abstractcomponent;

public class ProductCatalogue extends Abstractcomponent
{
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By productsBy = By.cssSelector(".mb-3");
	By productValue=By.cssSelector("b");
	By addToCart =By.className("w-10");
	By toastMessage = By.cssSelector("#toast-container");
	
	//List<WebElement> products= driver.findElements(By.cssSelector(".mb-3"));
	
	public List<WebElement> getProductList()
	{	
		waitForElementToLocate(productsBy);
		System.out.println(products.size());
		return products;
		//List<WebElement> products= driver.findElements(By.cssSelector(".mb-3"));
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement prod= getProductList().stream().filter(product->
		product.findElement(productValue).getText().equals(productName)).findFirst().orElse(null);
		
		return prod;
	}
	
	public void addToCart(String productName) throws InterruptedException
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		
		waitForElementToLocate(toastMessage);
		System.out.println("waitForElementToLocate executed from addToCart Method");
		waitForVisibilityOfElement(spinner);
		System.out.println("waitForVisibilityOfElement");
		waitForInvisibilityOfElement(spinner);
		System.out.println("waitForInvisibilityOfElement");
	}
		
}
