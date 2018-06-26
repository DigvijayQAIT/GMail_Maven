package actionClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * @author Digvijay
 */

public class WaitClass {
	
	private WebDriver driver;
	private int time;
	
	public WaitClass(WebDriver driver, int time) {
		this.driver = driver;
		this.time = time;
	}
	
	public void waitOnXpath(String xPath) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
	}
}
