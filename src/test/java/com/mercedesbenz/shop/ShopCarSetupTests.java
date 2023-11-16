package com.mercedesbenz.shop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class ShopCarSetupTests {

	protected static WebDriver driver;

	@Parameters({ "appUrl", "browser" })
	@BeforeTest(alwaysRun = true )
	public void setUp(String appUrl, @Optional("chrome") String browser) {

//		Create driver
		switch (browser) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("force-device-scale-factor=0.75");
			driver = new ChromeDriver(chromeOptions);
			break;

		case "firefox":
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setPreference("layout.css.devPixelsPerPx", "0.75");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setProfile(firefoxProfile);
			driver = new FirefoxDriver(firefoxOptions);
			break;
			
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("force-device-scale-factor=0.75");
			driver = new EdgeDriver(edgeOptions);
			break;

		default:
			System.out.println("Do not know how to start " + browser + ", starting chrome instead");
			driver = new ChromeDriver();
			break;
		}

		System.out.println("Browser started");

//		Maximize the browser window
		driver.manage().window().maximize();

//		Open test page
		driver.get(appUrl);
		
//		Wait for browser to open.
		sleep(10);
		
		System.out.println("Page is opened");
	}	


	@AfterTest(alwaysRun = true )
	public void tearDown() {
//		Close browser
		driver.quit();
	}
	
	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
