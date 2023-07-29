package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;

//In test class, we don't write any selenium code. It's all written inside Page class.

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	
	//log4j --write this line in all test classes
	private final Logger logger = Logger.getLogger(ProductInfoPageTest.class);

	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	

	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"MacBook", "MacBook Pro", 4},
			{"iMac", "iMac", 3},
			{"Apple", "Apple Cinema 30\"", 6},
			{"Samsung", "Samsung Galaxy Tab 10.1", 7}
			};
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage	= searchPage.selectProduct(productName);
		int actProductImageCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actProductImageCount, imagesCount );
	}
	
	@Test
	public void productInfoTest() {
		searchPage = accPage.performSearch("MacBook");
		productInfoPage	= searchPage.selectProduct("MacBook Pro");
		
		Map<String, String> actualProductInfoMap = productInfoPage.getProductInfo();
		//System.out.println(actualProductInfoMap); /moved to product page class
																		//softAssert gives opportunity to run following softAsserts and not fail at the same time.
		softAssert.assertEquals(actualProductInfoMap.get("Brand"), "Apple");	//Hard assertions --it will fail if put with "Apple11" and following assertion will not be executing
		softAssert.assertEquals(actualProductInfoMap.get("Product Code"), "Product 18"); //Soft Assertions should be used in this case when we're verifying multiple features common place
		softAssert.assertEquals(actualProductInfoMap.get("productName"), "MacBook Pro");
		softAssert.assertEquals(actualProductInfoMap.get("productPrice"), "$2,000.00");
		
		softAssert.assertAll(); //at the end, this is mandatory to write otherwise it won't tell which got failed.  
	}
	
	/* Assert vs Verify (Soft assertion) --Part of TestNG (unit testing framework)
	 * 
	 */
	
	@DataProvider
	public Object[][] addToCartTestData() {
		return new Object[][] {
			{"MacBook", "MacBook Pro", 2},
			{"iMac", "iMac", 3}
			};
	}
	
	
	@Test(dataProvider = "addToCartTestData")
	public void addToCartTest(String product, String productName, int productQty) {
		searchPage = accPage.performSearch(product);
		productInfoPage	= searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(productQty);
		String actCartMsg = productInfoPage.addProductToCart(); //Success: You have added MacBook Pro to your shopping cart!
		Assert.assertEquals(actCartMsg, "Success: You have added "+productName+" to your shopping cart!");
	}
	
	
}
