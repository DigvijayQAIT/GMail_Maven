package actionClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


/*
 * @author Digvijay
 */


public class LoginPageActions {
	
	WebDriver driver;
	JavascriptExecutor js;
	
	public LoginPageActions(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor)this.driver;
	}

	public void launchLoginPage(String string) {
		driver.get(string);
		System.out.println("User Launched URL: " + string);
	}

	public void verifyLoginPageLaunched() {
		WebElement logo = driver.findElement(By.xpath("//div[@id = 'logo']"));
		Assert.assertTrue(logo.isDisplayed());
		System.out.println("User is on Login Page");
	}

	public void sendEmailAndClickNext() {
		js.executeScript("document.getElementById('identifierId').value='vj.vns1707';");
//		js.executeScript("document.getElementById('identifierNext').click();");
		WebElement emailNext = (WebElement) js.executeScript("return document.getElementById('identifierNext')");
		emailNext.click();
		System.out.println("User entered email and clicked next");
	}

	public void sendPasswordAndLogin() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
		js.executeScript("document.querySelector('input[type = \"password\"]').value = 'myCROWsoft@1831';");
		js.executeScript("document.getElementById('passwordNext').click();");
	}

}
