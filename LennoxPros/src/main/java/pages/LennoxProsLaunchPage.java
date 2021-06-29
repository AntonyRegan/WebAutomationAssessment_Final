package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import common.LennoxProsMethods;

public class LennoxProsLaunchPage extends LennoxProsMethods{
	public LennoxProsLaunchPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using = "//a[text()='Sign In']")
	private WebElement signInLink;
	
	public LoginPage clickSignIn() {
		javascriptClick(signInLink);
		return new LoginPage();
	}
}
