package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtils eleUtil;

	// Private By locators
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By productQty = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMsg = By.cssSelector("div.alert-success");

	// Meta data HashMap
	private Map<String, String> productInfoMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}

	// product header
	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("Product header value is " + productHeaderVal);
		return productHeaderVal;
	}

	// verify images
	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsToBeVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT)
				.size();
		System.out.println("Product images count: " + imagesCount);
		return imagesCount;
	}

	// Product info
	public Map<String, String> getProductInfo() {

	//	productInfoMap = new HashMap<String, String>();//As HashMap Doesn't maintain any order then we can also use Linked HashMap
		productInfoMap = new LinkedHashMap<String, String>(); //"LinkedHashMap" maintains the order and same sequence
	
	//	productInfoMap = new TreeMap<String, String>(); //Sorted order/alphabetic order then we can use "TreeMap". Sort keys
		
		// Header data
		productInfoMap.put("productName", getProductHeaderValue());
		
		//Call below two split methods
		getProductMetaData();
		getProductPriceData();
		
		System.out.println(productInfoMap);
		
		return productInfoMap;
	}

	// For Meta Data
	private void getProductMetaData() {

		// Meta Data
		/*
		 * Brand: Apple Product Code: Product 18 Reward Points: 800 Availability: In
		 * Stock
		 */

		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String meta = e.getText(); // Brand: Apple

			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim(); // Brand
			String value = metaInfo[1].trim(); // Apple

			productInfoMap.put(key, value);
		}
	}

	// For Price data
	private void getProductPriceData() {

		// Price Data
		/*
		 * $2,000.00 Ex Tax: $2,000.00
		 */
		List<WebElement> priceList = eleUtil.getElements(productPriceData);

		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String extaxVal = exTax.split(":")[1].trim();

		// Store inside HashMap
		productInfoMap.put("productPrice", price); // create custom key if not available
		productInfoMap.put("exTax", extaxVal);
	}
	
	//Qty update
	public void enterQuantity(int qty) {
		System.out.println("Product qty " + qty);
		eleUtil.doSendKeys(productQty, String.valueOf(qty)); //convert int->String
	}

	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMsg = eleUtil.waitForElementToBeVisible(cartSuccessMsg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		
		//Strings are immutable 
		//testing --this will be there inside constant pool
		//testin
		
		//Using StringBuilder--Mutable
		StringBuilder sb = new StringBuilder(successMsg);
		String msg = sb.substring(0, successMsg.length()-1).replace("\n", "").toString();
		
		System.out.println("Cart success message is " + msg);
		return msg; //Doing this because there was an 'x' at the end of success message
	}
}
