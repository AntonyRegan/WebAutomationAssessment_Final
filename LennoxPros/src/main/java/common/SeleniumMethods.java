package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Reporter;

public class SeleniumMethods extends Reporter implements WebdriverMethods{
	public static WebDriver driver;
	public String appUrl;
	public Properties prop;
	public JavascriptExecutor js;
	
	
	  public SeleniumMethods() { 
		  prop = new Properties();
			try {
				prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
				System.out.println("Property == "+prop.getProperty("ApplicationURL"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  }
	 
	
	public void startApp(String browser) {
		try {
			if(browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				driver = new ChromeDriver();
			}else if(browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}else {
				/*
				 * DesiredCapabilities dc = new DesiredCapabilities();
				 * dc.setCapability("ignoreProtectedModeSettings", true);
				 * dc.setCapability("ignoreZoomSetting", true);
				 */
				InternetExplorerOptions options = new InternetExplorerOptions()
						.requireWindowFocus();
				options.ignoreZoomSettings();
				
				System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver(options);
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(prop.getProperty("ApplicationURL"));
			reportStep("The browser: "+browser+" launched successfully", "PASS");
		} catch (WebDriverException e) {
			reportStep("The browser: "+browser+" could not be launched", "FAIL");
		}
	}

	public WebElement locateElement(String locator, String locValue) {
		try {
			switch (locator) {
			case("id"): return driver.findElement(By.id(locValue));
			case("link"): return driver.findElement(By.linkText(locValue));
			case("xpath"): return driver.findElement(By.xpath(locValue));
			case("name"): return driver.findElement(By.name(locValue));
			case("class"): return driver.findElement(By.className(locValue));
			case("tag"): return driver.findElement(By.tagName(locValue));
			}
		} catch (NoSuchElementException e) {
			reportStep("The element with locator "+locator+" not found.","FAIL");
		} catch(WebDriverException e) {
			reportStep("Unknown exception occured while finding "+locator+" with the value "+locValue, "FAIL");
		}
		return null;
	}

	public void type(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
			reportStep("The data: "+data+" entered successfully in the field :"+ele, "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The data: "+data+" could not be entered in the field :"+ele,"FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while entering "+data+" in the field :"+ele, "FAIL");
		}
	}

	public void click(WebElement ele) {
		//String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));			
			//text = ele.getText();
			ele.click();
			//reportStep("The element "+text+" is clicked", "PASS");
			reportStep("The element is clicked", "PASS");
		} catch (InvalidElementStateException e) {
			//reportStep("The element: "+text+" could not be clicked", "FAIL");
			reportStep("The element: could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while clicking in the field :", "FAIL");
		}
		
	}

	public String getTextValue(WebElement ele) {
		String text = "";
		try {
			text = ele.getText().trim();
		} catch (WebDriverException e) {
			reportStep("The element: "+ele+" could not be found.", "FAIL");
		}
		return text;
	}

	public void verifyExactText(WebElement ele, String expectedText) {
		try {
			if(getTextValue(ele).equals(expectedText)) {
				reportStep("The text: "+getTextValue(ele)+" matches with the value :"+expectedText,"PASS");
			}else {
				reportStep("The text "+getTextValue(ele)+" doesn't matches the actual "+expectedText,"FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the Text", "FAIL");
		} 
	}

	@Override
	public void closeAllBrowsers() {
		try {
			driver.quit();
			reportStep("The opened browsers are closed","PASS", false);
		} catch (Exception e) {
			reportStep("Unexpected error occured in Browser","FAIL", false);
		}
	}

	@Override
	public void javascriptClick(WebElement ele) {
		try {
			js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", ele);
			reportStep("The element is clicked", "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The element: could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while clicking in the field :", "FAIL");
		}
	}

	@Override
	public WebElement locateElement(By byValue) {
		return driver.findElement(byValue);
	}

	@Override
	public Object javascriptGetText(String value) {
		js = (JavascriptExecutor)driver;
		return js.executeScript("var value = document.evaluate(\""+value+"\",document, null, XPathResult.STRING_TYPE, null); return value.stringValue;");
	}
	
	public String getAttribute(WebElement ele, String attribute) {		
		String returnVal = "";
		try {
			returnVal=  ele.getAttribute(attribute);
			System.out.println("Inside getAttribute method == "+returnVal);
		} catch (WebDriverException e) {
			reportStep("The element: "+ele+" could not be found.", "FAIL");
		} 
		return returnVal;
	}

	@Override
	public void waitForElementToBePresent(WebElement ele, int timeInSeconds) {
		new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(ele));
	}
	
}
