package SeleniumTraning.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTestSantosh 

{

	public static void main(String[] args) throws InterruptedException 
	
	{
		
		String productName="ADIDAS ORIGINAL";
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
				
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.manage().window().maximize();
		
		driver.get("https://rahulshettyacademy.com/client");
	
		driver.findElement(By.id("userEmail")).sendKeys("santosh@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("S@ntosh1");
		driver.findElement(By.name("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		List<WebElement> products= driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod= products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL")).findFirst().orElse(null);
		
		//prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		prod.findElement(By.className("w-10")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		WebElement prod1= products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals("IPHONE 13 PRO")).findFirst().orElse(null);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		prod1.findElement(By.className("w-10")).click();
		
		//prod.findElement(By.xpath("//div/div/div/button[2]")).click();
		
	    //prod.findElement(By.xpath("//div[@class='card-body']/button[2]")).click();
					
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        
        //Thread.sleep(3000);
        
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
        
        List<WebElement> cart=driver.findElements(By.cssSelector(".cart h3"));
        
        boolean match = cart.stream().anyMatch(cartprod->cartprod.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);
        
        driver.findElement(By.cssSelector(".subtotal button")).click();
        
//        driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("India");
//        List<WebElement> countries = driver.findElements(By.cssSelector(".ta-item.list-group-item.ng-star-inserted"));
//        System.out.println(countries.size());
//        WebElement countryreq = countries.stream().filter(country->country.findElement(By.cssSelector("span.ng-star-inserted")).getText().equals("India")).findFirst().orElse(null);
//        countryreq.click();
        
        Actions a = new Actions(driver);
        
        a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "India").build().perform();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        
        driver.findElement(By.xpath("(//span[@class='ng-star-inserted'])[2]")).click();
        
        driver.findElement(By.cssSelector(".action__submit")).click();
        
        String confrimMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        
        Assert.assertTrue(confrimMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        
        driver.close();
        
                    
      }

}
