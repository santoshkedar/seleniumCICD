package SeleniumTraning.tests;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import selenium.Utilities.ExcelFunctions;

public class FileConversionExcelToPDF extends ExcelFunctions
{
	@Test
	public void FileConversionExcelToPDFTC()
	{
		String downLoadpath =System.getProperty("user.dir");
		WebDriverManager.chromedriver().setup();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	    chromePrefs.put("profile.default_content_settings.popups", 0);
	    chromePrefs.put("download.default_directory", downLoadpath);
	    ChromeOptions options = new ChromeOptions();
	    options.setExperimentalOption("prefs", chromePrefs);
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get("https://www.ilovepdf.com/excel_to_pdf");
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickfiles")));
		//clickSelectFile.click();
			
		WebElement upLoad = driver.findElement(By.cssSelector("input[type='file']"));
		
		upLoad.sendKeys("/Users/santoshkedar/Downloads/download.xlsx");
		
		driver.findElement(By.id("processTask")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("download")));
		
		driver.findElement(By.id("download")).click();
		
		File f = new File(downLoadpath+"/download.pdf");
		if(f.exists())
		{
			Assert.assertTrue(true);
			f.delete();
		}
				
		driver.close();
		
	}
}
