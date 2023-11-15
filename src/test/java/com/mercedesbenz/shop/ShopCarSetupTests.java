package com.mercedesbenz.shop;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ShopCarSetupTests {

	protected static WebDriver driver;

	@Parameters({ "appUrl", "browser" })
	@BeforeSuite(alwaysRun = true )
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


	@AfterSuite(alwaysRun = true )
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
