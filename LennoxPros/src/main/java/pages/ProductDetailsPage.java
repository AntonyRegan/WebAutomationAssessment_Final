package pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.openqa.selenium.support.PageFactory;

import common.LennoxProsMethods;

public class ProductDetailsPage extends LennoxProsMethods{
	Map<String, String> previousProductDetails;
	
	public ProductDetailsPage(Map<String, String> productDetails) {
		PageFactory.initElements(driver, this);
		previousProductDetails = productDetails;
	}
	
	public ProductDetailsPage productComparison() {
		Map<String, String> productDetails = new LinkedHashMap<String, String>();
		String prodDesc = getTextValue(locateElement("xpath", "//div[@class='OT-product-shop']/h1"));
		String catNo = getTextValue(locateElement("xpath", "//p[@class='sku']//span"));
		String modelPartNo = getTextValue(locateElement("xpath", "//div[@class='OT-product-shop']//span[@class='pdp-model-number']"));
		String yourPrice = getTextValue(locateElement("xpath", "(//p[@class='price']//span)[1]"));
		String listPrice = javascriptGetText("(//p[@class='price']//span)[2]/following::text()[1]").toString();
		String standAvailability = getTextValue(locateElement("xpath", "//span[contains(@class,'availability-info-ot')]"));
		String pickUpAvailability = getTextValue(locateElement("xpath", "//div[@class='availability-info-ot']/strong"));
		String zipcode = javascriptGetText("(//div[contains(@class,'availability-info')]//span)[3]/text()").toString();
		
		System.out.println("PickupAvailability in Product Details page == "+pickUpAvailability);
		
		//System.out.println("Zip code after javascript === "+zipcode.substring(0, zipcode.indexOf("(")));
				//getTextValue(locateElement("xpath", "//div[contains(@class,'availability-info')]//p[2]/span"));
		
		String addToCartStatus = "";
		if(getAttribute(locateElement("xpath", "//button[contains(@class,'button-primary')]"), "disabled") == null) {
			addToCartStatus = "Enabled";
		}else {
			addToCartStatus = "Disabled";
		}
		System.out.println("add to card status == "+addToCartStatus);
		
		productDetails.put("ProductDescription", prodDesc.trim());
		productDetails.put("CatNo", catNo.trim());
		productDetails.put("ModelPartNo", modelPartNo.trim());
		productDetails.put("YourPrice", yourPrice.trim());
		productDetails.put("ListPrice", listPrice.substring(listPrice.indexOf("$")).trim());
		productDetails.put("StandardShippingAvailability", standAvailability.trim());
	//	productDetails.put("PickupStoreAvailability", pickUpAvailability.trim());
		productDetails.put("ZipCode", zipcode.substring(0, zipcode.indexOf("(")).trim());
		productDetails.put("AddToCartStatus", addToCartStatus.trim());
		
		
		for (Map.Entry<String, String> each : previousProductDetails.entrySet()) {
			System.out.println("-------------------");
			System.out.println("Previous Product Details Page Map1 -- Key -- "+each.getKey()+"||||||"+" Product Details page Value -- "+each.getValue());
		}
		System.out.println("=============================");
		for (Map.Entry<String, String> each : productDetails.entrySet()) {
			System.out.println("-------------------");
			System.out.println("Current Product Details Page Map1 -- Key -- "+each.getKey()+"||||||"+" Product Details page Value -- "+each.getValue());
		}
	//	System.out.println("Zipcode ---- "+zipcode.substring(0, zipcode.indexOf("(")));
		
		if(new ArrayList<>( previousProductDetails.values() ).equals(new ArrayList<>( productDetails.values() ))) {
			System.out.println("Product Details are matched");
			reportStep("Product details are matched", "PASS");
		}else {
			System.out.println("Product Details are not matched");
			reportStep("Product details are not matched", "PASS");
		}
		
		return this;
	}
}
