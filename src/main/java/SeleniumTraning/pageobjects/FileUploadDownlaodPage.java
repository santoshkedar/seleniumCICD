package SeleniumTraning.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumTraining.abstractcomponent.Abstractcomponent;

public class FileUploadDownlaodPage extends Abstractcomponent
{
	WebDriver driver;
	String message;
	String priceColumnID;
	String fruitColumnID;
	//String beforeUploadValue;
	//String AfteruploadValue;
	public FileUploadDownlaodPage(WebDriver driver) 
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="downloadButton")
	WebElement downLoadFileButton;
		
	@FindBy(css="#row-1 div:nth-child(4) div")
	WebElement itemValue;
	
	@FindBy(css=".sc-jsEeTM.itluUR.rdt_TableRow")
	List<WebElement> items;
		
	@FindBy(css="input[type='file']")
	WebElement uploadFileButton;
	
	@FindBy(css=".Toastify__toast-body div:nth-child(2)")
	WebElement toastMessage;
	
	@FindBy(xpath="//div[text()='Price']")
	WebElement priceColumnIDElement;
	
	@FindBy(xpath="//div[text()='Fruit Name']")
	WebElement fruitNameColumnIDElement;
	
	By itemsBy = By.cssSelector(".sc-jsEeTM.itluUR.rdt_TableRow");
			
	public void launchURL()
	{
		driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
	}
	
	public void fileDownload()
	{			
		downLoadFileButton.click();
	}
	
	public List<WebElement> getProductList()
	{	
		waitForElementToLocate(itemsBy);
		return items;
	}
	
	public String getItemValue(String itemName)
	{	
		
		fruitColumnID = fruitNameColumnIDElement.getAttribute("data-column-id");
		WebElement prod= getProductList().stream().filter(item->
		item.findElement(By.id("cell-"+fruitColumnID+"-undefined")).getText().equals(itemName)).findFirst().orElse(null);
		priceColumnID = priceColumnIDElement.getAttribute("data-column-id");
		return prod.findElement(By.id("cell-"+priceColumnID+"-undefined")).getText(); 
	}
	
	public String fileupload() throws InterruptedException
	{
		uploadFileButton.sendKeys("/Users/santoshkedar/Downloads/download.xlsx");
		waitForVisibilityOfElement(toastMessage);
		message = toastMessage.getText();
		waitForInvisibilityOfElement(toastMessage);
		return message;
	}
	
}
