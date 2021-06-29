package common;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface WebdriverMethods {
	public void startApp(String browser) throws MalformedURLException ;
	public WebElement locateElement(String locator, String locValue);
	public WebElement locateElement(By byValue);
	public void type(WebElement ele, String data);
	public void click(WebElement ele);
	public String getTextValue(WebElement ele);
	public void verifyExactText(WebElement ele, String expectedText);
	public void closeAllBrowsers();
	public void javascriptClick(WebElement ele);
	public Object javascriptGetText(String strValue);
	public String getAttribute(WebElement ele, String attribute);
	public void waitForElementToBePresent(WebElement ele, int timeInSeconds);
}
