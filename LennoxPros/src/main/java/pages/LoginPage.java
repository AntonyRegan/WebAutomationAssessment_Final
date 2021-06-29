package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import common.LennoxProsMethods;
import utils.DataInputProvider;

public class LoginPage extends LennoxProsMethods{
	
	DataInputProvider dp = new DataInputProvider(dataSheetName);
	
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "j_username")
	private WebElement userName;
	
	public LoginPage enterUserName(String data) {
		type(userName, data);
		return this;	
	}
	
	@FindBy(how = How.ID, using = "j_password")
	private WebElement password;
	
	public LoginPage enterPassword(String data) {
		type(password, data);
		return this;
	}
	
	@FindBy(how = How.XPATH, using = "//button[text()='Sign In']")
	private WebElement signInBtn;
	
	public HomePage clickSignInBtn() {
		//click(signInBtn);
		javascriptClick(signInBtn);
		return new HomePage();
	}
}
