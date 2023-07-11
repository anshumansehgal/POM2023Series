package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class SearchPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	//Private By locators
	private By searchProductResults = By.cssSelector("div#content div.product-layout");

	//constructor
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	//Find no. of search results
	public int getSearchProductCount() {
		int productCount = eleUtil.waitForElementsToBeVisible(searchProductResults, AppConstants.DEFAULT_SHORT_TIME_OUT).size();
		System.out.println("Product count: " + productCount);
		return productCount;
	}
	
	/**
	 * Select Product based on given Product name. 
	 * @param productName
	 * @return
	 */
	public ProductInfoPage selectProduct(String productName) {
		
		By productLocator = By.linkText(productName);
		eleUtil.waitForElementToBeVisible(productLocator, AppConstants.DEFAULT_MEDIUM_TIME_OUT).click();
		
		return new ProductInfoPage(driver); // create next landing page class obj. whenever we click
	}

}
