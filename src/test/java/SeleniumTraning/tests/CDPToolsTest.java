package SeleniumTraning.tests;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v121.emulation.Emulation;
import org.openqa.selenium.devtools.v121.fetch.Fetch;
import org.openqa.selenium.devtools.v121.network.Network;
import org.openqa.selenium.devtools.v121.network.model.ConnectionType;
import org.openqa.selenium.devtools.v121.network.model.ErrorReason;
import org.openqa.selenium.devtools.v121.network.model.Request;
import org.openqa.selenium.devtools.v121.network.model.Response;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CDPToolsTest 
{
	
	// Sample TC to set Device metrics like width, height, scale  
	
	@Test	
	public void CDPMobileTestEmulation()
	{
		
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Emulation.setDeviceMetricsOverride(600, 1000, 50, true, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), 
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("navbar-toggler-icon")));
		
		driver.findElement(By.className("navbar-toggler-icon")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Library")));
		
		driver.findElement(By.partialLinkText("Library")).click();
		
		driver.close();
	}
	
	// Sample TC to directly invoke CDP command. This is required when Selenium don't provide class/method to call CDP Command
	// Method - executeCdpCommand(Actual command, parameters)
	
	@Test	
	public void CDPCommandTest()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		Map <String,Object>deviceMetrics = new HashMap<String,Object>();
		deviceMetrics.put("width", 600);
		deviceMetrics.put("height", 1000);
		deviceMetrics.put("deviceScaleFactor", 50);
		deviceMetrics.put("mobile", true);
		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("navbar-toggler-icon")));
		
		driver.findElement(By.className("navbar-toggler-icon")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Library")));
		
		driver.findElement(By.partialLinkText("Library")).click();
		
		driver.close();
	}
	
	// Sample TC to change Geolocation 
		
	@Test	
	public void CDPChangeGeoLocation()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		//devTools.send(Emulation.setGeolocationOverride(Optional.of(40), Optional.of(3), Optional.of(1)));
		
		//Using Execute CDP Command directly from Selenium to trigger Chrome DevTool options

		Map<String,Object> coordinates=new HashMap<String,Object>();

		coordinates.put("latitude", 18);

		coordinates.put("longitude", 73);

		coordinates.put("accuracy", 1);

		//Change the location to different country (here spain)

		driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
		
		driver.get("https://www.google.com/");
		
		driver.findElement(By.name("q")).sendKeys("netflix",Keys.ENTER);
		
		driver.findElement(By.cssSelector(".LC20lb")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[data-uia='nmhp-card-hero-text-title']")));
		
		String title = driver.findElement(By.cssSelector("h1[data-uia='nmhp-card-hero-text-title']")).getText();
		
		System.out.println(title);
			
		driver.close();
	}
	
	//TC to check status code of request sents, URLs, 
	
	@Test	
	public void NetworkActivityTC()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.addListener(Network.requestWillBeSent(), request ->
						{
							Request reqObject = request.getRequest();
							//System.out.println(reqObject.getUrl());
						}
						);
		
		devTools.addListener(Network.responseReceived(), response -> 
						{
							Response responseObject = response.getResponse();
							//System.out.println(responseObject.getUrl());
							//System.out.println(responseObject.getStatus());
							if(responseObject.getStatus().toString().startsWith("4"))
							{
								System.out.println(responseObject.getUrl() + "is failing with" + responseObject.getStatus());
							}
						}
						);
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='/library']")));
		
		driver.findElement(By.cssSelector("[routerlink*='/library']")).click();
					
		driver.close();
	}
	
	//TC to mock data from response and use it in TC
	
	@Test	
	public void NetworkResponseUpdate()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		//Enables issuing of requestPaused events
		
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		
		// Listen for events
		
		devTools.addListener(Fetch.requestPaused(), request ->
						{
							// Paused request to update URL with new data
							if(request.getRequest().getUrl().contains("=shetty"))
							{
								String mockedURL = request.getRequest().getUrl().replace("=shetty", "=Badguy");
								System.out.println("MockedURL is = " + mockedURL);
								devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockedURL), Optional.of(request.getRequest().getMethod()), 
										Optional.empty(), Optional.empty(), Optional.of(true)));
							}
							else 
							{
								devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()), Optional.of(request.getRequest().getMethod()), 
										Optional.empty(), Optional.empty(), Optional.of(true)));
							}
						}
						);
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='/library']")));
		
		driver.findElement(By.cssSelector("[routerlink*='/library']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p")));
		
		String actualMessage= driver.findElement(By.cssSelector("p")).getText();
		
		System.out.println(actualMessage);
		
		Assert.assertEquals(actualMessage, "Oops only 1 Book available");
						
		driver.close();
	}
	
	//TC to purposefully fail the test
	
	@Test	
	public void NetworkFailRequest()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
				
		//java.util.Optional<java.lang.String> urlPattern
		
	    Optional<List<org.openqa.selenium.devtools.v121.fetch.model.RequestPattern>>patterns = Optional.of(Arrays.asList(new org.openqa.selenium.devtools.v121.fetch.model.RequestPattern(Optional.of("*GetBook*"),Optional.empty(),Optional.empty())));
		devTools.send(Fetch.enable(patterns, Optional.empty()));
		
		//Enables issuing of requestPaused events
		
		devTools.addListener(Fetch.requestPaused(), request ->
					{
						// Fail request
						
						devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
						
					}
					);

		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='/library']")));
		
		driver.findElement(By.cssSelector("[routerlink*='/library']")).click();
									
		driver.close();
	}
	
	//TC to purposefully block some requests
	
	@Test	
	public void NetworkBlockRequests()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
				 
		devTools.send(Network.enable(Optional.empty(),Optional.empty(), Optional.empty()));
		
		//steps to block certain URLs based on reqs. Here jog and CSS urls are blocked
				 
		devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg", "*.css")));
		
		long startTime = System.currentTimeMillis();
			
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='/library']")));
		
		driver.findElement(By.cssSelector("[routerlink*='/products']")).click();
		driver.findElement(By.linkText("Selenium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		
		System.out.println(driver.findElement(By.className("sp")).getText());
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(endTime-startTime);
		
		driver.close();
	}
	
	//TC to simulate network speed
	
	@Test	
	public void NetworkSpeedSimulation()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
				 
		devTools.send(Network.enable(Optional.empty(),Optional.empty(), Optional.empty()));
		
		//steps to block certain URLs based on reqs. Here jog and CSS urls are blocked
				 
		devTools.send(Network.emulateNetworkConditions(false, 3000, 20000, 100000, Optional.of(ConnectionType.ETHERNET)));
		
		long startTime = System.currentTimeMillis();
			
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='/library']")));
		
		driver.findElement(By.cssSelector("[routerlink*='/library']")).click();
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(endTime-startTime);
		
		driver.close();
	}
	
	
	//TC to simulate network condition - internet connectivity 
	
	@Test	
	public void NetworkFailedResponseCapture()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
				 
		devTools.send(Network.enable(Optional.empty(),Optional.empty(), Optional.empty()));
		
		devTools.send(Network.emulateNetworkConditions(true, 3000, 20000, 100000, Optional.of(ConnectionType.ETHERNET)));
		
		//steps to capture failed steps details
				 
		devTools.addListener(Network.loadingFailed(), loadingFailed ->
						{
							System.out.println(loadingFailed.getErrorText());
							System.out.println(loadingFailed.getTimestamp());
						});
			
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		driver.close();
	}
	
	//TC to perform basic authentication
	
	@Test	
	public void BasicAuthentication()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
	
		Predicate <URI> uriPredicate = uri -> uri.getHost().contains("httpbin.org");
		
	    ((HasAuthentication)driver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));
			
		driver.get("http://httpbin.org/basic-auth/foo/bar");
		
		driver.close();
	}
	
	//TC to capture console logs details 
	
	@Test	
	public void ConsoleLogsCapture()
	{
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
					
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.linkText("Browse Products")).click();
		driver.findElement(By.linkText("Selenium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		
		driver.findElement(By.cssSelector("[routerlink*='/products']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Appium")));
		
		driver.findElement(By.linkText("Appium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		
		driver.findElement(By.linkText("Cart")).click();
		driver.findElement(By.id("exampleInputEmail1")).clear();
		driver.findElement(By.id("exampleInputEmail1")).sendKeys("2");
		
		//Get LogEntry objects
		
		LogEntries entry = driver.manage().logs().get(LogType.BROWSER);
		// getAll method returns all the logs
		
		List<LogEntry> logs = entry.getAll();
				 
		for(LogEntry e : logs)
		{
			System.out.println(e.getMessage());
		}
		
		//driver.close();
	}
}
