package com.mercedesbenz.shop;


import com.mercedesbenz.shop.utils.SeleniumUtils;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ShopHomePageTests extends ShopCarSetupTests {


	/**
	 * Validate and accept all cookies.
	 * 
	 * @param failedAssertionCookiesMessage contains the message content that will
	 *                                      be displayed if the cookies menu do not
	 *                                      close successfully
	 */
	@Parameters({ "failedAssertionCookiesMessage" })
	@Test()
	public void acceptCookiesTest(String failedAssertionCookiesMessage) {

//		Test case: acceptCookiesTest
//		Open page (at BeforeMethod).

		System.out.println("acceptCookiesTest started");


	//		Get element inside DOM shadow root.	
		WebElement shadowHost = driver.findElement(By.tagName("cmm-cookie-banner"));
		SearchContext shadowRoot = shadowHost.getShadowRoot();
		
//		Wait 5 seconds for the "Agree to all" cookies button to be clickable.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement agreeAllCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(
				shadowRoot.findElement(By.cssSelector("wb7-button[data-test='handle-accept-all-button']"))));

//		Click "Agree to All" button.
		agreeAllCookiesButton.click();
		
//		Validate if the "Agree to all" button is still visible.
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
		Assert.assertTrue(wait1.until(ExpectedConditions.invisibilityOf(agreeAllCookiesButton)),
				failedAssertionCookiesMessage);

		System.out.println("acceptCookiesTest finished");
	}
	

	@Parameters({ "selectedStateLocation", "insertedPostalCode", "expectedRadioBoxText",
			"failedAssertionLocationFormMessage" })
	@Test
	public void fillLocationFormTest(String selectedStateLocation, String insertedPostalCode,
			String expectedRadioBoxText, String failedAssertionLocationFormMessage) {

//		Test case: fillLocationFormTest
//		Open page (at BeforeMethod).
//		Accept All Cookies (dependency on acceptCookiesTest).

		System.out.println("fillLocationFormTest started");

//		Find state select element.
		WebElement stateSelectElement = driver
				.findElement(By.xpath("//wb-select-control[@class='dcp-header-location-modal-dropdown hydrated']"));

//		Close dropdown state location element.
		stateSelectElement.click();

//		Find interactable field in wb-select element.
		WebElement interactableSelectElement = stateSelectElement.findElement(By.xpath("//wb-select/select"));
		interactableSelectElement.sendKeys(selectedStateLocation);

//		Close dropdown state location element.
		stateSelectElement.click();

//		Find postal code input element. 
		
		WebElement postalCodeInputElement = driver
				.findElement(By.xpath("//wb-input-control[@class='hydrated']/wb-input/input"));
		
		Actions builder = new Actions(driver);
		builder.moveToElement(postalCodeInputElement).perform();		
		postalCodeInputElement.sendKeys(insertedPostalCode.substring(0, insertedPostalCode.length()-1));
		postalCodeInputElement.sendKeys(insertedPostalCode.substring(insertedPostalCode.length()-1, insertedPostalCode.length()));
		
//		Get all elements from radio box.
		List<WebElement> purposeRadioElements = driver
				.findElements(By.xpath("//div[@class='dcp-radio__options-container']/wb-radio-control"));

//		Search for desired radio box and click it.
		for (WebElement purposeRadioElement : purposeRadioElements) {

			if (purposeRadioElement.getText().equals(expectedRadioBoxText)) {
				purposeRadioElement.findElement(By.xpath("//div[@class='wb-radio-control__indicator']")).click();
			}
		}

//		Wait 5 seconds for the "Continue" button to be clickable.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
				driver.findElement(By.xpath("//button[@data-test-id='state-selected-modal__close']"))));

//		 "Continue" button is clicked.
		continueButton.click();

//		Verify location form element is no longer displayed
		WebElement locationFormElement = driver.findElement(By.xpath("//div[@data-test-id='modal-popup__location']"));
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		Assert.assertTrue(wait1.until(ExpectedConditions.invisibilityOf(locationFormElement)),
				failedAssertionLocationFormMessage);

		System.out.println("fillLocationFormTest finished");
	}

	@Parameters({ "failedAssertionCarFilterFormMessage" })
	@Test
	public void openCarFilterFormTest(String failedAssertionCarFilterFormMessage) {

//		Test case: openCarFilterFormTest
//		Open page (at BeforeMethod).
//		Accept All Cookies and fill location form. Dependency on fillLocationFormTest.

		System.out.println("openCarFilterFormTest started");

//		Wait 5 seconds for the car filter button to be clickable.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement carFilterButton = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//span[@class='filter-toggle']"))));
		carFilterButton.click();

//		Wait 5 seconds for the car filter form to be visible.	
		WebElement carFilterFormElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sidebar-filter']")));

//		Validate if the car filter form is shown.
		Assert.assertTrue(carFilterFormElement.isDisplayed(), failedAssertionCarFilterFormMessage);

		System.out.println("openCarFilterFormTest finished");
	}

	@Test
	public void fillCarFilterFormTest() {

		System.out.println("fillCarFilterFormTest started");

//		Test case: fillCarFilterFormTest
//		Open page (at BeforeMethod).
//		Accept All Cookies, fill location form and open car filter form (Dependency on openCarFilterFormTest).
//		Switch to Pre-owned tab.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement preOwnedtab = wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//wb-tab[@name='0']/button"))));

		preOwnedtab.click();

//		Wait until Pre-Owned form is shown.
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//wb-tab[@name='0']/button")));

//		Wait for loader.
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));

//		Wait until colour button is shown.
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement colourOption = wait3
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='fab-filter']/div[7]")));

//		Click to open colour options selector.	
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait4.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='fab-filter']/div[7]")));
		colourOption.click();

//		Click to see the colour options available.
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement colourDropdown = wait5.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//div[@class='fab-filter']/div[7]/div/div[@class='category-filter-row__container']/div/div[@data-test-id='multi-select-dropdown']/a")));
		colourDropdown.click();

//		Click on the first available colour.
		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement selectedColour = wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//div[@class='fab-filter']/div[7]/div/div[@class='category-filter-row__container']/div/div[@data-test-id='multi-select-dropdown']/ul/li/a")));

		String selectedColourText = selectedColour.getText();
		selectedColour.click();

//		Wait for loader.
		WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait7.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));

//		Validate if filter panel is correctly displaying the selected colour.
		WebDriverWait wait8 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement filterPanel = wait8.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@data-test-id='dcp-selected-filters-widget-tag']")));
		String filterPanelText = filterPanel.getText();

		Assert.assertTrue(filterPanelText.contains(selectedColourText),
				"The filter does not contains the selected colour.\nExpected colour: " + selectedColourText
						+ "\n Current colour: " + filterPanelText);

		System.out.println("fillCarFilterFormTest finished");
	}

	@Parameters({ "sortingFilterType" })
	@Test
	public void sortingCarFilterTest(String sortingFilterType) {

		System.out.println("sortingCarFilterTest started");

//		Click on sorting filter dropdown.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement sortingFilterDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//wb-select-control[@class='dcp-cars-srp__sorting-dropdown hydrated']")));

		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait1.until(ExpectedConditions.elementToBeClickable(sortingFilterDropdown));
		sortingFilterDropdown.click();

//		Wait for loader.
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));

		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement sortingListOrder = wait3.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//wb-select-control[@class='dcp-cars-srp__sorting-dropdown hydrated']/wb-select/select")));

		Actions builder = new Actions(driver);
		builder.moveToElement(sortingListOrder).perform();	
		sortingListOrder.sendKeys(sortingFilterType);
		
		builder.moveToElement(sortingListOrder).perform();
		sortingListOrder.sendKeys(Keys.ENTER);

//		Wait for loader.
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait4.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));

		System.out.println("sortingCarFilterTest finished");
	}

	@Test
	public void selectFirstCarAvailableTest() {

		System.out.println("selectFirstCarAvailableTest started");

//		Count number of cars listed.
		List<WebElement> carsTileList = driver.findElements(By.xpath("//div[@class='dcp-cars-srp-results__tile']"));
		Assert.assertTrue(carsTileList.size() > 0, "There is no cars for the selected filters.");

//		Explore first car shown in the list.
		WebElement selectedCar = carsTileList.get(0).findElement(By.xpath("//div[@class='wb-button-text']"));
		selectedCar.click();

//		Wait for new page to be visible.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dcp-vehicle-details-category__list dcp-vehicle-details-list']/li")));
				
		
		System.out.println("selectFirstCarAvailableTest finished");
	}






}
