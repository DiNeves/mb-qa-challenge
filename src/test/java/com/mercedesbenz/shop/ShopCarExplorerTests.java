package com.mercedesbenz.shop;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mercedesbenz.shop.utils.SystemUtils;



public class ShopCarExplorerTests extends ShopCarSetupTests {

	@Parameters({ "carTechSpecification", "exportFilePath" })
	@Test
	public void getCarTechDetailTest(String carTechSpecification, String exportFilePath) {

//		Test case: findVinNumberAndModelYearTest
//		Open page (at BeforeMethod).

		System.out.println("getCarTechDetailTest started");
		//			Wait for data to be displayed.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//li[@data-test-id='dcp-vehicle-details-list-item-1']/span[1]"), "ANCAP Year"));

//		Get all information information from car.
		List<WebElement> carTechDetails = driver.findElements(
				By.xpath("//ul[@class='dcp-vehicle-details-category__list dcp-vehicle-details-list']/li"));

//		Get specific information from car.
		List<String> items = Stream.of(carTechSpecification.split(";")).collect(Collectors.toList());

		String carInfoForfileMessage = new String();
	
		for (WebElement carDataLine : carTechDetails) {
			
			WebElement header = carDataLine.findElement(By.className("dcp-vehicle-details-list-item__label"));
			for (String item : items) {
				if (header.getText().contains(item)) {
					WebElement data = carDataLine.findElement(By.className("dcp-vehicle-details-list-item__value"));
					carInfoForfileMessage += header.getText() + ":" + data.getText() + ";";
					System.out.println(header.getText() + ":" + data.getText());
				}
			}
		}
		
		SystemUtils.writeToFile(exportFilePath, carInfoForfileMessage, false);
		
		System.out.println("getCarTechDetailTest finished");
	}
	
	@Parameters({ "failedAssertionEnquireNowFormMessage" })
	@Test
	public void openEnquireNowFormTest(String failedAssertionEnquireNowFormMessage) {

		System.out.println("openEnquireNowFormTest started");

//		Wait for loader.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));
		
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement enquireNowbutton = wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[@data-test-id='dcp-buy-box__contact-seller']")));
				
		enquireNowbutton.click();
		
//		Wait for loader.
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dcp-loader']")));
		
//		Wait 5 seconds for the "Enquire Now" form to be visible.	
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement enquireNowFormElement = wait3.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='dcp-rfq-contact__heading']")));

//		Validate if the "Enquire Now" form is shown.
		Assert.assertTrue(enquireNowFormElement.isDisplayed(), failedAssertionEnquireNowFormMessage);

		System.out.println("openEnquireNowFormTest finished");
	}

	@Parameters({ "firstName", "lastName", "email", "phone", "postalCode", "comments", "privacy", "directMarktSmsMmsIm",
			"directMarktPhone", "directMarktEmail", "errorMessageAssertionEnquireNowForm" })
	@Test
	public void fillEnquireNowCarFormNegativeTest(String firstName, String lastName, String email, String phone,
			String postalCode, String comments, boolean privacy, boolean directMarktSmsMmsIm, boolean directMarktPhone,
			boolean directMarktEmail, String errorMessageAssertionEnquireNowForm) {

		System.out.println("fillEnquireNowCarFormNegativeTest started");
		Actions builder = new Actions(driver);
		
//		Wait 10 seconds for the "Enquire Now" form to be loaded.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement firstNameElement = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__first-name']/wb-input-control/wb-input/input")));
				
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement lastNameElement = wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__last-name']/wb-input-control/wb-input/input")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement emailElement = wait2.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__email']/wb-input-control/wb-input/input")));
		
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement phoneElement = wait3.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__phone']/wb-input-control/wb-input/input")));
		
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement postalCodeElement = wait4.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__postal-code']/wb-input-control/wb-input/input")));
		
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement commentsElements = wait5.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__comments']/wb-input-control/wb-input/textarea")));
		
		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement privacyElement = wait6.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='dcp-multi-checkbox'][1]/div/div/wb-checkbox-control/label/wb-icon")));
		
		WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement directMarktSmsMmsImElement = wait7.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__consent-marketing']/div/div[1]/wb-checkbox-control/label/wb-icon")));
		
		WebDriverWait wait8 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement directMarktPhoneElement = wait8.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__consent-marketing']/div/div[2]/wb-checkbox-control/label/wb-icon")));

		WebDriverWait wait9 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement directMarktEmailElement = wait9.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@data-test-id='rfq-contact__consent-marketing']/div/div[3]/wb-checkbox-control/label/wb-icon")));
		
		WebDriverWait wait10 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement proceedButtonElement = wait10.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[@data-test-id='dcp-rfq-contact-button-container__button-next']")));
		
		builder.moveToElement(firstNameElement).click().perform();
		firstNameElement.sendKeys(firstName);

		builder.moveToElement(lastNameElement).perform();
		lastNameElement.sendKeys(lastName);

		emailElement.sendKeys(email);

		phoneElement.sendKeys(phone);

		postalCodeElement.sendKeys(postalCode);

		commentsElements.sendKeys(comments);

		if (privacy) {
			privacyElement.click();
		}

		if (directMarktSmsMmsIm) {
			directMarktSmsMmsImElement.click();
		}

		if (directMarktPhone) {
			directMarktPhoneElement.click();
		}

		if (directMarktEmail) {
			directMarktEmailElement.click();
		}
		
		proceedButtonElement.click();
		
		
		WebDriverWait wait11 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement errorMessageElement = wait11.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='dcp-error-message']")));
		
		String currentErrorMessageDisplayed = errorMessageElement.getText();
		Assert.assertTrue(currentErrorMessageDisplayed.contains(errorMessageAssertionEnquireNowForm),
				"The error message is not correct.\n Expected message: " + errorMessageAssertionEnquireNowForm + "\nCurrent message: "
						+ currentErrorMessageDisplayed);

		System.out.println("fillEnquireNowCarFormNegativeTest finished");
	}

	
}
