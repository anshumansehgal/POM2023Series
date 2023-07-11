package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@value='1']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@value='0']");
	
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit']");
	
	private By registerSuccessMsg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	//Constructor
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	
	public boolean registerUser(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		
		eleUtil.waitForElementToBeVisible(this.firstName, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmpassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doActionsClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);
		
		String successMsg = eleUtil.waitForElementToBeVisible(registerSuccessMsg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		System.out.println("User registration success message " + successMsg);
		
		if(successMsg.contains(AppConstants.USER_REG_SUCCESS_MSG)) {
			
			//click on logout btn and then register link 
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			
			return true;
		}
		return false;
		
	}
	

}
