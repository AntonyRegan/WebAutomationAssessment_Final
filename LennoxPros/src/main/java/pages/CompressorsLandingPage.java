package pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import common.LennoxProsMethods;
import utils.DataInputProvider;

public class CompressorsLandingPage extends LennoxProsMethods {
	DataInputProvider dp = new DataInputProvider(dataSheetName);
	
	String pageDesc = dp.getCellData("PageDescription", runningTestcaseName);
	String catNoData = dp.getCellData("CatNo", runningTestcaseName);
	
	Map<String, String> productDetails = new LinkedHashMap<String, String>();
	
	public CompressorsLandingPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = "//div[@class='description']/p")
	private WebElement description;

	public CompressorsLandingPage verifyDescription() {
		String actual = getTextValue(description);
		Assert.assertEquals(actual, pageDesc);
		return this;
	}

	@FindBy(how = How.XPATH, using = "//div[@id='resultsList']//li")
	private List<WebElement> allProducts;

	public ProductDetailsPage searchProduct() {
		

		for (int i = 1; i <= allProducts.size(); i++) {
			
			  Object catNo= javascriptGetText("//div[@id='resultsList']//li["+i+"]//div[@class='sku']/span/following::text()[1]");
			  System.out.println("CAT # ==== "+catNo.toString());
			 
			  if(catNo.toString().trim().equals(catNoData)) {
				  System.out.println("Product found "); 
				  String productDesc = getTextValue(locateElement("xpath", "//div[@id='resultsList']//li["+i+"]//h2")); 
				  System.out.println("Prod Desc == "+productDesc);
				  
				  Object modelPartNo = javascriptGetText("(//div[@id='resultsList']//li["+i+"]//div[@class='sku']/span/following::text()[1])[2]"); 
				  System.out.println("Mode No == "+modelPartNo.toString());
				  
				  String yourPrice = getTextValue(locateElement("xpath", "//div[@id='resultsList']//li["+i+"]//p[@class='your-price']"));
				  System.out.println("Your price == "+yourPrice);
				  
				  String lstPri = javascriptGetText("//div[@id='resultsList']//li["+i+"]//p[@class='list-price']//following::text()").toString();
				  String listPrice = lstPri.substring(0, lstPri.indexOf(","))+lstPri.substring(lstPri.indexOf(",")+1);
				  System.out.println("Final list price after concatenating === "+listPrice);
				
				  waitForElementToBePresent(locateElement("xpath", "//div[@id='resultsList']//li["+i+"]//div[contains(@class,'ship-to-availability')]"), 30);
				  String standardShipAvailability = getTextValue(locateElement("xpath", "//div[@id='resultsList']//li["+i+"]//div[contains(@class,'ship-to-availability')]"));
				  System.out.println("Find Element stdavailability == "+standardShipAvailability);
				  
				  String pickupStoreAvailability = getTextValue(locateElement("xpath", "//div[@id='resultsList']//li["+i+"]//div[contains(@class,'local-availability')]"));
				  System.out.println("pickup availability == "+pickupStoreAvailability);
				  
				  String zipCode = getTextValue(locateElement("xpath", "(//div[@id='resultsList']//li["+i+"]//span[@class='zip-replace'])[2]"));
				  System.out.println("zip code == "+zipCode);
				  
				  String addToCartStatus = "";
				  if(getAttribute(locateElement("xpath", "//div[@id='resultsList']//li["+i+"]//button[contains(@class,'add-to-cart')]"), "disabled") == null) {
					  addToCartStatus = "Enabled";
				  }else {
					  addToCartStatus = "Disabled";
				  }
				  System.out.println("add to card status == "+addToCartStatus);
			  
				  //Additing into Map collection
				  productDetails.put("ProductDescription", productDesc.trim());
				  productDetails.put("CatNo", catNo.toString().trim());
				  productDetails.put("ModelPartNo", modelPartNo.toString().trim());
				  productDetails.put("YourPrice", yourPrice.trim()); 
				  productDetails.put("ListPrice", listPrice.trim()); 
				  productDetails.put("StandardShippingAvailability", standardShipAvailability.trim()); 
				  productDetails.put("PickupStoreAvailability", pickupStoreAvailability.trim()); 
				  productDetails.put("ZipCode", zipCode.trim()); 
				  productDetails.put("AddToCartStatus", addToCartStatus.trim());
				  
			  
				  javascriptClick(locateElement("xpath", "//div[@id='resultsList']//li["+i+"]//img"));
			  
				  break; 
			  }
			  
			  if(i == allProducts.size()){ 
				  List<WebElement> nextBtn = driver.findElements(By.xpath("//a[contains(@class,'arrow-right')]"));
				  if(!nextBtn.isEmpty()) {
					  if(driver.findElement(By.xpath("//a[contains(@class,'arrow-right')]")).isDisplayed()) { 
						  javascriptClick(locateElement("xpath", "//a[contains(@class,'arrow-right')]"));
						  //jse.executeScript("arguments[0].click()",driver.findElement(By.xpath("//a[contains(@class,'arrow-right')]")));
						  allProducts.clear(); 
						  allProducts = driver.findElements(By.xpath("//div[@id='resultsList']//li")); i=1; 
					  } 
				  }else {
					  reportStep("Product is not found", "PASS");
					  System.out.println("Product Not Found"); 
				  } 
			  }
			 
		}
		return new ProductDetailsPage(productDetails);
	}
}
