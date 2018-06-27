package actionClasses;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.google.common.base.CharMatcher;

/*
 * @author Digvijay
 */

public class MailPageActions {

	WebDriver driver;
	JavascriptExecutor executor;
	WaitClass waitObj;
	String strCount;
	List<WebElement> unreadEmails;
	int emails;

	public MailPageActions(WebDriver driver) {
		this.driver = driver;
		executor = (JavascriptExecutor) this.driver;
		waitObj = new WaitClass(driver, 5);
	}

	public void closeUpcomingOption() {
		executor.executeScript("document.querySelector(\"a[href = 'javascript:void(0)']\").click();");
	}

	@SuppressWarnings("deprecation")
	public int unreadEmailCount() {
		strCount = CharMatcher.digit()
				.retainFrom(executor.executeScript("return document.querySelector(('a.J-Ke.n0')).title;").toString());
		int countUnread;
		if (strCount.equals("")) {
			countUnread = 0;
			System.out.println("Total Unread Mails: " + countUnread);
			return countUnread;
		}
		countUnread = Integer.parseInt(strCount);
		System.out.println("Total Unread Mails: " + countUnread);
		return countUnread;
	}

	public void openAnUnreadEmailIrrespectiveOfThePage() throws InterruptedException {
		if (unreadEmailCount() == 0) {
			System.out.println("No Unread Mails");
			return;
		} else {
			emails = totalUnreadEmailsOnPage();
			if (emails == 0) {
				openNextPage();
			} else {
				searchUnreadEmailAndOpenIt();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private int totalUnreadEmailsOnPage() {
		unreadEmails = (List<WebElement>) executor.executeScript("return document.getElementsByClassName('zA zE')");
		int unreadEmailCountOnPage = unreadEmails.size();
		System.out.println("Total unread emails on page: " + unreadEmailCountOnPage);
		return unreadEmailCountOnPage;
	}

	public void openNextPage() throws InterruptedException {
		WebElement nextPage = (WebElement) executor.executeScript("return document.querySelector(\"span[class = 'Di'] > div[aria-label = 'Older']\");");
		nextPage.click();
		System.out.println("User switch page");
		waitObj.waitOnXpath("//tr[@class = 'zA zE']");
		openAnUnreadEmailIrrespectiveOfThePage();
	}

	private void searchUnreadEmailAndOpenIt() throws InterruptedException {
		for (WebElement unreadEmail : unreadEmails) {
			unreadEmail.click();
			System.out.println("User opened new unread mail");
			Thread.sleep(2000);
			executor.executeScript("document.querySelector('div.aim.ain>div').click();");
			--emails;
			break;
		}
	}

	public void verifyEmailCountChanges(int beforeOpeningMail, int afterOpeningMail) {
		if (beforeOpeningMail == 0) {
			Assert.assertEquals(beforeOpeningMail, afterOpeningMail);
		} else {
			if (beforeOpeningMail > afterOpeningMail) {
				Assert.assertNotEquals(beforeOpeningMail, afterOpeningMail);
				System.out.println("Email Count Decreases by 1");
				System.out.println();
			} else if (beforeOpeningMail == afterOpeningMail || beforeOpeningMail < afterOpeningMail) {
				System.out.println("No Changes - Failed");
				System.out.println();
				Assert.fail();
			}
		}
	}
}
