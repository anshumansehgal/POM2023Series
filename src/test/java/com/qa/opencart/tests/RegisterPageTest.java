package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass  //pre-condition
	public void regPageSetup() {
		registerPage = loginPage.navigateToRegister();
	}
	
	
	@DataProvider
	public Object[][] getRegTestData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	//creating method to generate random email id
	public String getRandomEmail() {
	
		//2 solutions - 
		
		//1st - using random but their is probability that same random might come
	//	Random random = new Random();
	//	String email = "automation" +random.nextInt(1000) + "@gmail.com";
		
		//2nd - using current time 
		String email = "automation" + System.currentTimeMillis() + "@gmail.com";
		return email;
	}
	
	
	//Email should be unique so we'll remove dependency from excel
	@Test(dataProvider = "getRegTestData")
	public void userRegTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage.registerUser(firstName, lastName, getRandomEmail(), telephone, password, subscribe));
	}
	
	
	
	
}
