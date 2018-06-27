 	package testClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import actionClasses.LoginPageActions;
import actionClasses.MailPageActions;

public class GMailTest {

	WebDriver driver;
	LoginPageActions loginPage;
	MailPageActions mailPage;

	@BeforeClass
	public void initVars() {
		driver = new ChromeDriver();
		loginPage = new LoginPageActions(driver);
		mailPage = new MailPageActions(driver);
	}

	@Test
	public void Step01_Launch_LoginPage() {
		loginPage.launchLoginPage("https://mail.google.com");
		loginPage.verifyLoginPageLaunched();
		loginPage.sendEmailAndClickNext("ENTER USERNAME");
		loginPage.verifyEmailEnteredAndNextClicked();
		loginPage.sendPasswordAndLogin("ENTER PASSWORD");
		loginPage.verifyPasswordEnteredAndUserLogined("https://mail.google.com/mail/u/0/#inbox");
	}
	
	@Test(dependsOnMethods = {"Step01_Launch_LoginPage"})
	public void Step02_Launch_MailPage() throws InterruptedException {
		mailPage.closeUpcomingOption();
		int beforeOpeningMail = mailPage.unreadEmailCount();
		mailPage.openAnUnreadEmailIrrespectiveOfThePage();
		int afterOpeningMail = mailPage.unreadEmailCount();
		mailPage.verifyEmailCountChanges(beforeOpeningMail, afterOpeningMail);
	}

	@AfterClass
	public void closeBrowser() {
		driver.close();
	}
}
