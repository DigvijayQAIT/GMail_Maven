package actionClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/*
 * @author Digvijay
 */

public class LoginPageActions {

	private WebDriver driver;
	private JavascriptExecutor executor;
	private WebElement emailField, emailNext, passwordField, passwordNext, emailHeading, passwordHeading;
	private String email, password, emailHeadingText, passwordHeadingText;
	private WaitClass waitObj;

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginPageActions(WebDriver driver) {
		this.driver = driver;
		executor = (JavascriptExecutor) this.driver;
		waitObj = new WaitClass(driver, 5);
	}

	public void launchLoginPage(String string) {
		driver.get(string);
		System.out.println("User Launched URL: " + string);
	}

	public void verifyLoginPageLaunched() {
		emailHeading = (WebElement) executor.executeScript("return document.getElementById(\"headingText\");");
		emailHeadingText = emailHeading.getText();
		WebElement logo = driver.findElement(By.xpath("//div[@id = 'logo']"));
		Assert.assertTrue(logo.isDisplayed());
		System.out.println("User is on Login Page");
	}

	public void sendEmailAndClickNext(String userEmail) {
		setEmail(userEmail);
		emailField = (WebElement) executor.executeScript("return document.getElementById('identifierId');");
		emailField.sendKeys(userEmail);
		emailNext = (WebElement) executor.executeScript("return document.getElementById('identifierNext')");
		emailNext.click();
		System.out.println("User entered email and clicked next");
	}

	public void verifyEmailEnteredAndNextClicked() {
		String email = emailField.getAttribute("value");
		Assert.assertEquals(this.email, email);
		System.out.println("Email entered - Verified");

		waitObj.waitOnXpath("//span[text() = 'Forgot password?']");
		passwordHeading = (WebElement) executor.executeScript("return document.getElementById(\"headingText\");");
		passwordHeadingText = passwordHeading.getText();
		Assert.assertNotEquals(emailHeadingText, passwordHeadingText);
		System.out.println("Next Button Clicked");

	}

	public void sendPasswordAndLogin(String userPassword) {
		setPassword(userPassword);
		waitObj.waitOnXpath("//input[@type='password']");
		passwordField = (WebElement) executor.executeScript("return document.querySelector('input[type = \"password\"]');");
		passwordField.sendKeys(userPassword);
		passwordNext = (WebElement) executor.executeScript("return document.getElementById('passwordNext')");
		passwordNext.click();
		System.out.println("User entered password and clicked next");
	}

	public void verifyPasswordEnteredAndUserLogined(String expectedURL) {
		String password = passwordField.getAttribute("value");
		Assert.assertEquals(this.password, password);
		System.out.println("Password entered - Verified");

		waitObj.waitOnXpath("//img[@class = 'gb_1a']");
		String actualURL = driver.getCurrentUrl();
		Assert.assertEquals(actualURL, expectedURL);
		System.out.println("User Logined");
		System.out.println();
	}
}
