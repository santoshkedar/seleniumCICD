package SelenumTraning.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SeleniumTraning.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest 
{
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver intializeDriver() throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//SeleniumTraining//resources//GlobalData.properties");
		prop.load(fis);
		
		String browserName=System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		
	//	String browserName = prop.getProperty("browser");
		
		if(browserName.contains("chrome"))
		{	
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			
			if(browserName.contains("headless"))
			{
				options.addArguments("headless");	   		    
			}
			 driver = new ChromeDriver(options);
			 driver.manage().window().setSize(new Dimension(1440,900));
		
		}
		else if(browserName.contains("edge"))
		{
			EdgeOptions edgeOption = new EdgeOptions();
			System.setProperty("webdriver.edge.driver", "/Users/santoshkedar/Desktop/Drivers/msedgedriver");
			
			if(browserName.contains("headless"))
			{
				edgeOption.addArguments("headless");	   		    
			}
			
			 driver = new EdgeDriver(edgeOption);
			 driver.manage().window().setSize(new Dimension(1440,900));
		}
		else if(browserName.contains("firefox"))
		{
			FirefoxOptions fireFoxOption = new FirefoxOptions();
			System.setProperty("webdriver.gecko.driver", "/Users/santoshkedar/Desktop/Drivers/geckodriver");
			
			if(browserName.contains("headless"))
			{
				fireFoxOption.addArguments("headless");	   		    
			}
			
			 driver = new FirefoxDriver(fireFoxOption);
			 driver.manage().window().setSize(new Dimension(1440,900));
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		return driver;
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		System.out.println("Executed");
		driver=intializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//read json file to string
		String jsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);
		
		//string to Hashmap import Jackson Databind dependency
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
		
		return data;
		
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException
	{
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source= ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//"+ testCaseName+ ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"//reports//"+ testCaseName+ ".png";
		
		
//	    String screenshotPath = null;
//
//        try {
//
//            //take screenshot and save it in a file
//
//            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//
//            //copy the file to the required path
//
//            File destinationFile = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
//
//            FileHandler.copy(sourceFile, destinationFile);
//
//            String[] relativePath = destinationFile.toString().split("reports");
//
//            screenshotPath = ".\\" + relativePath[1];
//
//        } catch (Exception e) {
//
//            System.out.println("Failure to take screenshot " + e);
//
//        }
//
//        return screenshotPath;
		
		
	}
			
}
