package com.mercedesbenz.shop;


import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement shadowHost =wait.until(ExpectedConditions.presenceOfElementLocated(
				By.tagName("cmm-cookie-banner")));
		
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait1.until(ExpectedConditions.attributeToBe(
				By.tagName("cmm-cookie-banner"),"class", "hydrated"));	
		
//		Wait for loader.
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait2.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='dcp-loader']")));
		
		SearchContext shadowRoot = shadowHost.getShadowRoot();	
		

		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait3.until(ExpectedConditions.visibilityOf(shadowRoot.findElement(
				By.cssSelector("wb7-button[data-test='handle-accept-all-button']"))));

		
//		Wait 5 seconds for the "Agree to all" cookies button to be clickable.
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement agreeAllCookiesButton=wait4.until(ExpectedConditions.elementToBeClickable(shadowRoot.findElement(
				By.cssSelector("wb7-button[data-test='handle-accept-all-button']"))));

//		Click "Agree to All" button.
		agreeAllCookiesButton.click();
		
//		Validate if the "Agree to all" button is still visible.
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(15));
		Assert.assertTrue(wait5.until(ExpectedConditions.invisibilityOf(agreeAllCookiesButton)),
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

		Actions builder = new Actions(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement stateSelectElement =wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//wb-select-control[@class='dcp-header-location-modal-dropdown hydrated']")));
		
		Select interactableSelectElement = new Select(stateSelectElement.findElement(
				By.xpath("./wb-select/select")));
				
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement postalCodeInputElement =wait2.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//wb-input-control[@class='hydrated']/wb-input/input")));
		
//		Get all elements from radio box.
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		List<WebElement> purposeRadioElements =wait3.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//div[@class='dcp-radio__options-container']/wb-radio-control")));
		
		

		
		interactableSelectElement.selectByVisibleText(selectedStateLocation);
		
//		Find postal code input element. 	
		builder.moveToElement(postalCodeInputElement).click().perform();		
		postalCodeInputElement.sendKeys(insertedPostalCode);
				//.substring(0, insertedPostalCode.length()-1));
		//postalCodeInputElement.sendKeys(insertedPostalCode.substring(insertedPostalCode.length()-1, insertedPostalCode.length()));
		
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait4.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//wb-control-error")));
		

//		Search for desired radio box and click it.
		for (WebElement purposeRadioElement : purposeRadioElements) {

			if (purposeRadioElement.getText().equals(expectedRadioBoxText)) {			
				purposeRadioElement.findElement(By.xpath("//div[@class='wb-radio-control__indicator']")).click();
			}
		}

//		Wait for the "Continue" button to be clickable.
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement continueButton = wait5.until(ExpectedConditions.elementToBeClickable(driver.findElement(
				By.xpath("//button[@data-test-id='state-selected-modal__close']"))));

//		 "Continue" button is clicked.
		builder.moveToElement(continueButton).click().perform();

//		Verify location form element is no longer displayed
		WebElement locationFormElement = driver.findElement(By.xpath("//div[@data-test-id='modal-popup__location']"));
		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(10));
		Assert.assertTrue(wait6.until(ExpectedConditions.invisibilityOf(locationFormElement)),
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

//		Wait for the car filter button to be clickable.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//span[@class='filter-toggle']")));		
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement carFilterButton = wait1.until(ExpectedConditions.elementToBeClickable(driver.findElement(
				By.xpath("//span[@class='filter-toggle']"))));
		
		carFilterButton.click();

//		Wait for the car filter form to be visible.	
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait2.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='sidebar-filter']")));		
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement carFilterFormElement = wait3.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='sidebar-filter']")));

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
		
		String selectedColourIndexer ="1";
		String selectedColourText ="";
		
//		Switch to Pre-owned tab.
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='sidebar-filter']")));
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement preOwnedtab = wait1.until(ExpectedConditions.elementToBeClickable(driver.findElement(
				By.xpath("//wb-tab[@name='0']/button"))));

		preOwnedtab.click();

//		Wait until Pre-Owned form is shown.
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait2.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//wb-tab[@name='0']/button")));

//		Wait for loader.
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='dcp-loader']")));

//      Navigate to colour option
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(15));
		List<WebElement> preOwnedOptionsList = wait4.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//div[@data-v-0b8abf29 and @class='fab-filter']/div")));
		
		for (WebElement option : preOwnedOptionsList) {
					
				if (option.findElement(By.xpath("./div/div[@class='category-filter-row-headline']/p"))
						.getText().contentEquals("Colour")) {
					//Click to open colour options selector.	
					WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(15));
					wait5.until(ExpectedConditions.elementToBeClickable(option));
					
					option.click();
					
//					Click to see the colour options available.
					WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(15));
					WebElement colourDropdown = wait6.until(ExpectedConditions.visibilityOf(option.findElement(
							By.xpath("./div/div[@class='category-filter-row__container']/div/div[@data-test-id='multi-select-dropdown']/a"))));
					colourDropdown.click();
					
//					Click on the first available colour.
					WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(15));
					WebElement selectedColour = wait7.until(ExpectedConditions.elementToBeClickable(option.findElement(
							By.xpath("./div/div[@class='category-filter-row__container']/div/div[@data-test-id='multi-select-dropdown']/ul/li["+selectedColourIndexer+"]/a"))));

					selectedColourText = selectedColour.getText();
					selectedColour.click();
					
					break;
				}	
		}
		

//		Wait for loader.
		WebDriverWait wait8 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait8.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));

//		Validate if filter panel is correctly displaying the selected colour.
		WebDriverWait wait9 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement filterPanel = wait9.until(ExpectedConditions
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

		Select sortingListOrder = new Select(driver.findElement(
				By.xpath("//wb-select-control[@class='dcp-cars-srp__sorting-dropdown hydrated']/wb-select/select")));
		
		sortingListOrder.selectByVisibleText(sortingFilterType);
		

//		Wait for loader.
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait4.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));

		System.out.println("sortingCarFilterTest finished");
	}

	@Test
	public void selectFirstCarAvailableTest() {

		System.out.println("selectFirstCarAvailableTest started");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='dcp-cars-srp-results__tile']")));
		
//		Count number of cars listed.
		List<WebElement> carsTileList = driver.findElements(By.xpath("//div[@class='dcp-cars-srp-results__tile']"));
		Assert.assertTrue(carsTileList.size() > 0, "There is no cars for the selected filters.");

//		Explore first car shown in the list.
		WebElement selectedCar = carsTileList.get(0).findElement(By.xpath("//div[@class='wb-button-text']"));
		selectedCar.click();

		
//		Wait for new page to be visible.
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//ul[@class='dcp-vehicle-details-category__list dcp-vehicle-details-list']/li")));
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait2.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//ul[@class='dcp-vehicle-details-category__list dcp-vehicle-details-list']/li")));

		System.out.println("selectFirstCarAvailableTest finished");
	}






}
