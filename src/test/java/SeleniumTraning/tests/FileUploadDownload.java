package SeleniumTraning.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import SeleniumTraning.pageobjects.FileUploadDownlaodPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import selenium.Utilities.ExcelFunctions;

public class FileUploadDownload extends ExcelFunctions
{
	
	String confirmationMessage;
	String item="Banana";
	String fileName = "/Users/santoshkedar/Downloads/download.xlsx";
	int fieldValue=400;
	
	@Test
	public void fileUploadTestCase() throws InterruptedException, IOException
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		
		FileUploadDownlaodPage FileUploadDownlaodPageObject = new FileUploadDownlaodPage(driver);
		
		FileUploadDownlaodPageObject.launchURL();
		
		String beforeUploadValue = FileUploadDownlaodPageObject.getItemValue(item);
		
		//File Download
		FileUploadDownlaodPageObject.fileDownload();
		
		int columnNumber = getColumnNumber(fileName,"Sheet1","Price");
		int rowNumber = getRowNumber(fileName,"Sheet1","fruit_name", item);
		
		System.out.println("Column Number: " + columnNumber + " And Row Number: " + rowNumber);
		
		Assert.assertTrue(updateExcelCell(fileName,"Sheet1",columnNumber,rowNumber,fieldValue));
				
		//File upload
		try 
		{
			confirmationMessage = FileUploadDownlaodPageObject.fileupload();
			Assert.assertEquals(confirmationMessage, "Updated Excel Data Successfully.");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int AfterUploadValue = Integer.parseInt(FileUploadDownlaodPageObject.getItemValue(item));

		Assert.assertEquals(AfterUploadValue,fieldValue);
		
		driver.close();
	}
}
