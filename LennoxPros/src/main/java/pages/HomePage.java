package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import common.LennoxProsMethods;
import utils.DataInputProvider;

public class HomePage extends LennoxProsMethods{
	DataInputProvider data = new DataInputProvider(dataSheetName);
	String lnkName = data.getCellData("LinkName", runningTestcaseName);
	String pageNav = data.getCellData("PageNavigation", runningTestcaseName).split(":")[0];
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'hmaburger')]/a")
	private WebElement hamBurgerMenu;
	
	public HomePage clickHamBurgerMenu() {
		click(hamBurgerMenu);
		return this;
	}
	
	By mainMenu = By.xpath("(//a[text()='"+lnkName+"'])[2]");
	public HomePage clickMainMenu() {
		click(locateElement(mainMenu));
		return this;
	}
	
	By subMenu = By.xpath("(//a[text()='"+pageNav+"'])[2]");
	
	public CompressorsPage clickSubMenu() {
		click(locateElement(subMenu));
		return new CompressorsPage();
	}
}
