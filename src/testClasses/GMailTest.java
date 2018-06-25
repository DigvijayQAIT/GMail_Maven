package testClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import actionClasses.LoginPageActions;

public class GMailTest {

	WebDriver driver;
	LoginPageActions loginPage;

	@BeforeClass
	public void initVars() {
		driver = new ChromeDriver();
		loginPage = new LoginPageActions(driver);
	}

	@Test
	public void Step01_Launch_LoginPage() {
		loginPage.launchLoginPage("https://mail.google.com");
		loginPage.verifyLoginPageLaunched();
		loginPage.sendEmailAndClickNext("vj.vns1707");
		loginPage.verifyEmailEnteredAndNextClicked();
		loginPage.sendPasswordAndLogin("myCROWsoft@1831");
		loginPage.verifyPasswordEnteredAndUserLogined("https://mail.google.com/mail/u/0/#inbox");
	}

	@AfterClass
	public void closeBrowser() {
		driver.close();
	}
}
