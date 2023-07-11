package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

import io.qameta.allure.Step;

public class LoginPage {
	
	//Blueprint says that you should your own driver, own private locators, own constructor and page actions 
	
	//Every page will have its driver
	private WebDriver driver;
	
	//Every page is also having its ElementUtil
	private ElementUtils eleUtil;
	
	/*
	 * Structure of Page class
	 * 1. Maintain the private By locators
	 */
	
	//1. Private By locators: Private info. we're hiding and giving access to public methods -- Encapsulation
	private By emailID = By.id("input-email");
	private By passwordID = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	//why making above private ? Anyone can use and change it's value | This cannot be used by other pages
	//Don't make it static ---because then method and driver also you want to make it static. It will create problem in parallel execution also because it will create 1 common copy

	//2. Page Constructor
	public LoginPage(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtils(driver);
	}
	
	//3. Page actions/Methods
	@Step("Getting the login page title")
	public String getLoginPageTitle() {
	//	String title = driver.getTitle();
		String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE_VALUE);
		return title;
	}
	
	@Step("Getting the login page URL")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		return url;
	}
	
	@Step("Getting the forgot password link")
	public boolean isForgotPwdLinkExist() {     
		return eleUtil.waitForElementToBeVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed(); //A private class variable used inside public method?  Encapsulation concept
	}
	
	@Step("login with username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String password) {
		System.out.println("App credentials are " + username + ":" + password);
		eleUtil.waitForElementToBeVisible(emailID, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(username);;
		eleUtil.doSendKeys(passwordID, password);
		eleUtil.doClick(loginBtn);
	
		//Landing on the new page after clicking above button
		//this method's responsibility to return the next landing page class object
		return new AccountsPage(driver); //TDD(test driven development approach) This concept is called Page chaining model in POM Design. 
										 //	This is a famous technique to return the next landing page class object.
	}
	
	//Navigate to Register page, should return next landing page class object
	@Step("Navigating to Register Page")
	public RegisterPage navigateToRegister() {
		eleUtil.doClick(registerLink);
		
		return new RegisterPage(driver);
	}
	
	public void doLoginwithWrongData(String username, String password) {
		System.out.println("App credentials are " + username + ":" + password);
		eleUtil.waitForElementToBeVisible(emailID, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(username);;
		eleUtil.doSendKeys(passwordID, password);
		eleUtil.doClick(loginBtn);
		
	//	return getText of Incorrect message and validate same in @Test class
	}
	
	
}
