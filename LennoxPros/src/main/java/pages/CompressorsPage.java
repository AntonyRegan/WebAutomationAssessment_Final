package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import common.LennoxProsMethods;
import utils.DataInputProvider;

public class CompressorsPage extends LennoxProsMethods{
	DataInputProvider dp = new DataInputProvider(dataSheetName);
	String pageNav = dp.getCellData("PageNavigation", runningTestcaseName).split(":")[1];
	
	public CompressorsPage() {
		PageFactory.initElements(driver, this);
	}
	
	By compressorsLnk = By.xpath("//ul[@class='menu filters']//a[text()='"+pageNav+"']");

	public CompressorsLandingPage clickCompressorsLink() {
		javascriptClick(locateElement(compressorsLnk));
		return new CompressorsLandingPage();
	}
}
