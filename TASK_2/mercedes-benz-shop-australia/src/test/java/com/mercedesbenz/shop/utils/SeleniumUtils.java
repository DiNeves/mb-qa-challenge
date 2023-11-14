package com.mercedesbenz.shop.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	public static void waitForLoader(WebDriver driver) {
		
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		
//		Wait for loader.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(pageLoadCondition);
	}
	
}
