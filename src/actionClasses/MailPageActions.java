package actionClasses;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * @author Digvijay
 */

public class MailPageActions {

	WebDriver driver;
	JavascriptExecutor executor;
	ArrayList<WebElement> list;
	WebElement countUnread;
	WaitClass waitObj;

	public MailPageActions(WebDriver driver) {
		this.driver = driver;
		executor = (JavascriptExecutor) this.driver;
		waitObj = new WaitClass(driver, 5);
		
	}

	@SuppressWarnings("unchecked")
	public void unreadEmailCount() {
		list = (ArrayList<WebElement>) executor.executeScript("return document.getElementsByClassName('bsU');");
		countUnread = list.get(0);
		System.out.println("Total Unread Emails: " + countUnread.getText());
	}

	@SuppressWarnings("unchecked")
	public void openAnUnreadEmailIrrespectiveOfThePage() {
		
		List<WebElement> unreadEmails = (List<WebElement>) executor
				.executeScript("return document.getElementsByClassName(\"zA zE\");");
		int unreadEmailCountOnPage = unreadEmails.size();
		System.out.println(unreadEmailCountOnPage);
		if (unreadEmailCountOnPage == 0) {
			openNextPage();
		} else {
			for (WebElement unreadEmail : unreadEmails) {
				String classType = unreadEmail.getAttribute("class");
				if (classType.equals("zA zE")) {
					unreadEmail.click();
					System.out.println("User opened new unread mail");
					WebDriverWait wait = new WebDriverWait(driver, 5);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'T-I J-J5-Ji lS T-I-ax7 W6eDmd']")));
//					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					WebElement backButton = (WebElement) executor.executeScript("return document.querySelector(\"div[class = 'T-I J-J5-Ji lS T-I-ax7 W6eDmd']\");");
					backButton.click();
//					driver.findElement(By.xpath("//div[@aria-label = 'Back to Inbox']")).click();
					--unreadEmailCountOnPage;
					break;
				} else if (classType.equals("zA yO")) {
					continue;
				}
			}
		}
	}

	public void openNextPage() {
		WebElement nextPage = (WebElement) executor
				.executeScript("return document.querySelector(\"span[class = 'Di'] > div[aria-label = 'Older']\");");
		nextPage.click();
		System.out.println("User switch page");
//		WebDriverWait wait = new WebDriverWait(driver, 5);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class = 'zA zE']")));
		waitObj.waitOnXpath("//tr[@class = 'zA zE']");
		System.out.println("Wait working perfectly");
		openAnUnreadEmailIrrespectiveOfThePage();
	}

	public void closeUpcomingOption() {
		executor.executeScript("document.querySelector(\"a[href = 'javascript:void(0)']\").click();");
	}

}
